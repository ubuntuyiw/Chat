const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();
const db = admin.firestore();

exports.notification = functions.firestore.document("/Message/{data}")
    .onCreate((data, _) => {
      const messageData = data.data();

      if (!messageData) {
        console.error("Invalid document data");
        return;
      }

      const {message, email} = messageData;
      const allTokens = [];
      // eslint-disable-next-line max-len
      const tokenToDoc = {}; // Store tokens and their associated document references

      return db.collection("Users").get()
          .then((snap) => {
            if (snap.empty) {
              console.error("No documents found in Users collection");
              return;
            }

            snap.docs.forEach((doc) => {
              const docData = doc.data();
              if (docData && docData.email !== email && docData.deviceToken) {
                docData.deviceToken.forEach((token) => {
                  allTokens.push(token);
                  tokenToDoc[token] = doc; // Store the document reference
                });
              }
            });

            const messages = allTokens.map((token) => ({
              data: {email, message},
              token: token,
            }));

            return admin.messaging().sendEach(messages);
          })
          .then((response) => {
            response.responses.forEach((resp, idx) => {
              if (!resp.success) {
                // eslint-disable-next-line max-len
                console.error("Failed to send notification to", allTokens[idx], "Error:", resp.error);

                // Handle invalid tokens
                const invalidToken = allTokens[idx];
                const doc = tokenToDoc[invalidToken];

                if (doc) {
                  console.log("geçersiz tokenlar", invalidToken);
                  console.log("geçersiz tokenlar doccc ", doc.email);
                  doc.ref.update({
                    // eslint-disable-next-line max-len
                    deviceToken: admin.firestore.FieldValue.arrayRemove(invalidToken),
                  }).then(() => {
                    console.log("Removed invalid token");
                  }).catch((error) => {
                    console.error("Error removing invalid token:", error);
                  });
                }
              }
            });

            console.log("Notification sent");
          })
          // eslint-disable-next-line max-len
          .catch((error) => console.error("Error sending notifications:", error));
    });


