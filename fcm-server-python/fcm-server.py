import firebase_admin
from firebase_admin import credentials

cred = credentials.Certificate("clase2023a-firebase-adminsdk-2pbsh-8929dba5ab.json")
firebase_admin.initialize_app(cred)

from firebase_admin import messaging

# This registration token comes from the client FCM SDKs.
registration_token = 'di08MPyJRDaWyWwVdPFBNM:APA91bF0KO4RymX5wSmwT49kOEB38GYD9sGeJ0gw2HfTpFW3rS9ULOTnFjL4fuABlox79jIb8_o7IQNJN8VF-nQkrCT_0U1iWStPOsq9Ng8KRcjdOE_PJGk'

# See documentation on defining a message payload.
message = messaging.Message(
    data={
        'score': '850',
        'time': '2:45',
    },
    token=registration_token,
)

# Send a message to the device corresponding to the provided
# registration token.
response = messaging.send(message)
# Response is a message ID string.
print('Successfully sent message:', response)