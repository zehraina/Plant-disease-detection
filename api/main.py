from fastapi import FastAPI, File, UploadFile, Form
import uvicorn
import numpy as np
from io import BytesIO
from PIL import Image
import tensorflow as tf
import requests
import cv2
app = FastAPI()

MODEL = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/2/model2.pb")
# endpoint = "http://localhost:8502/v1/models/potatoes_model:predict"
potataoClasses = ["Early Blight", "Late Blight", "Healthy"]

tomatoClasses=['Tomato : bacterial spot',
 'Tomato : early blight',
 'Tomato : healthy',
 'Tomato : late blight',
 'Tomato : leaf mold',
 'Tomato : septoria leaf spot',
 'Tomato : spider mites two-spotted spider mite',
 'Tomato : target spot',
 'Tomato : tomato mosaic virus',
 'Tomato : tomato yellow leaf curl virus']

cornClasses=['Corn : cercospora leaf spot gray leaf spot',
 'Corn : common rust',
 'Corn : healthy',
 'Corn : northern leaf blight']


# specifying end point
@app.get("/ping")
def ping():
    return "This is Plant Leaf Disease Prediction API."

  
def read_file_as_image(data) -> np.ndarray:
    image = np.array(Image.open(BytesIO(data)))
    image=cv2.resize(image,(224, 224))/255.0
    return image

  
@app.post("/predict")
async def predict(
    # UploadFile is a datatype here
        file: UploadFile = File(...),
        parameter: str=Form(...)):
    image = read_file_as_image(await file.read())
    img_batch = np.expand_dims(image, 0)

    
    predictions = MODEL.predict(img_batch)
    
    predicted_class = int(np.argmax(predictions[0]))
    #class_name=potataoClasses[predicted_class]
     
    confidence = np.max(predictions[0])

    
    print(predicted_class, confidence, parameter)  
    
    return {
        'class': predicted_class,
        'confidence': float(confidence)*100
    }


if __name__ == "__main__":
    uvicorn.run(app, host='localhost', port=8000)
