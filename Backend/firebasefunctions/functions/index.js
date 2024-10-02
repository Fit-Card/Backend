/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");
const {onDocumentCreated} = require("firebase-functions/v2/firestore");

// The Firebase Admin SDK to access Firestore.
const admin = require('firebase-admin');
const {initializeApp} = require("firebase-admin/app");
const {getFirestore} = require("firebase-admin/firestore");

initializeApp();


const db = getFirestore();

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

exports.helloWorld = onRequest((request, response) => {
  logger.info("Hello logs!", {structuredData: true});
  response.send("Hello from Firebase!");
});

exports.join = onRequest(async (req, res) => {
    const { userId, fcmToken } = req.body;

    if (!userId || !fcmToken) {
        console.error('Invalid request parameters:', { userId, fcmToken });
        return res.status(400).send('userId, fcmToken are required.');
    }

    try {
        await db
            .collection('users')
            .doc(userId)
            .set({
                token: fcmToken,                
            });
        console.log('User data saved to Firestore:', { userId, fcmToken });
        return res.status(200).send('User data saved successfully');
    } catch (error) {
        console.error('Error saving user data to Firestore:', error);
        return res.status(500).send('Error saving user data');
    }
});

exports.addCard = onRequest(async (req, res) => {
    const { userId, cardInfos } = req.body;

    if (!userId || !cardInfos) {
        return res.status(400).send('userId and cardInfos are required.');
    }

    try {
        const userDocRef = admin.firestore().collection('users').doc(userId);
        const userDoc = await userDocRef.get();

        if (!userDoc.exists) {
            return res.status(404).send('User not found');
        }

        const userData = userDoc.data();
        const existingCardInfos = userData.cardInfos || [];

        // 새로운 카드 정보를 기존 카드 목록에 추가
        const updatedCardInfos = [...existingCardInfos, ...cardInfos];

        // 업데이트된 카드 목록을 Firestore에 저장
        await userDocRef.update({
            cardInfos: updatedCardInfos
        });

        return res.status(200).send('Card added successfully');
    } catch (error) {
        console.error('Error updating cardInfos:', error);
        return res.status(500).send('Error updating cardInfos');
    }
});