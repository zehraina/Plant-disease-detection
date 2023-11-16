from fastapi import FastAPI, File, UploadFile, Form
import uvicorn
import numpy as np
from io import BytesIO
from PIL import Image
import tensorflow as tf
import requests
import os
import cv2

import google.generativeai as palm
import os

palm.configure(api_key=os.environ['GOOGLE_PALM_API_KEY'])
import textwrap

app = FastAPI()
#Defining Classes

cropLabel=["Potato", "Tomato", "Corn", "Apple", "Grapes"]
potataoClasses = ["Potato : Early Blight", "Potato : Late Blight", "Potato : Healthy"]

tomatoClasses= ['Tomato : Bacterial spot', 'Tomato : Early blight', 'Tomato : Late blight',
 'Tomato : Leaf Mold', 'Tomato : Septoria leaf spot', 'Tomato : Spider mites Two-spotted spider mite',
 'Tomato : Target Spot', 'Tomato : Tomato Yellow Leaf Curl Virus', 'Tomato : Tomato mosaic virus', 'Tomato : healthy']

cornClasses=['Corn : cercospora leaf spot gray leaf spot', 'Corn : common rust', 'Corn : northern leaf blight', 'Corn : healthy']

appleClasses=['Apple : Apple scab', 'Apple : Black rot', 'Apple : Cedar apple rust', 'Apple : healthy']

grapeClasses=['Grape : Black rot', 'Grape : Esca (Black Measles)', 'Grape : Leaf blight (Isariopsis Leaf Spot)', 'Grape : healthy']


#Loading Models
OOD_MODEL = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/OOD/ood6")
# endpoint = "http://localhost:8502/v1/models/potatoes_model:predict"
MODEL_0 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/0/0_Potato_mobileNet")
MODEL_1 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/1/1_Tomato_mobileNet")
MODEL_2 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/2/2_Corn_mobileNet")
MODEL_3 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/3/3_Apple_mobileNet")
MODEL_4 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/4/4_Grapes_mobileNet")

# specifying end point
@app.get("/ping")
def ping():
    return "This is Plant Leaf Disease Prediction API."



def format_text(text, width=70):
    wrapper = textwrap.TextWrapper(width=width)
    return '\n'.join(wrapper.wrap(text))

@app.get('/getInfo')
async def getInfo(content: str):
    response = palm.generate_text(prompt=content)
    res=response.result
    print(res)
    return res
  
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

    
    ood_pred=OOD_MODEL.predict(img_batch)
    ood_check=np.argmax(ood_pred[0])
    ood_confidence = np.max(ood_pred[0])*100
    if(ood_check==0):
        print("Junk Image",ood_check)
        print(ood_confidence)
        return {
            'class': "junk",
            'confidence': 0.01
        }
    crop=cropLabel[int(parameter)]
    if parameter=="0":
        predictions = MODEL_0.predict(img_batch)
        predicted_class = potataoClasses[int(np.argmax(predictions[0]))]
    elif parameter=="1":
        predictions = MODEL_1.predict(img_batch)
        predicted_class = tomatoClasses[int(np.argmax(predictions[0]))]
    elif parameter=="2":
        predictions = MODEL_2.predict(img_batch)
        predicted_class = cornClasses[int(np.argmax(predictions[0]))]
    elif parameter=="3":
        predictions = MODEL_3.predict(img_batch)
        predicted_class = appleClasses[int(np.argmax(predictions[0]))]
    elif parameter=="4":
        predictions = MODEL_4.predict(img_batch)
        predicted_class = grapeClasses[int(np.argmax(predictions[0]))]
    
    classNumber=int(np.argmax(predictions[0]))
    confidence = np.max(predictions[0])*100

    
    print(f"Crop: {crop}\nClassNum: {classNumber}\nClassLabel: {predicted_class}\nConfidence: {confidence}")  
    
    return {
        'class': predicted_class,
        'confidence': float(confidence)
    }


if __name__ == "__main__":
    uvicorn.run(app, host='localhost', port=8000)
