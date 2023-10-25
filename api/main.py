from fastapi import FastAPI, File, UploadFile

import uvicorn

import numpy as np
from io import BytesIO
from PIL import Image
import cv2
import tensorflow as tf


app = FastAPI()

#################################
model=tf.keras.models.load_model("../Plant Disease Detection model Building and testing/models/potato_leaf_disease_classification_model2.keras")
class_names=['Potato___Early_blight', 'Potato___Late_blight', 'Potato___healthy']

#################################
@app.get("/ping")
async def ping():
    return "Hello FastAPI is alive"

def read_file_as_image(data) -> np.ndarray:
    image=np.array(Image.open(BytesIO(data)))
    image=cv2.resize(image,(180,180))
    return image

@app.post("/predict")
async def predict( file: UploadFile = File(...) ):  
    bytes = await file.read()
    image=read_file_as_image(bytes)
    print(type(image))

    img_batch=np.expand_dims(image,axis=0)      # increasing the dimension of the image to 4D
    prediction=model.predict(img_batch)
    
    disease_type=class_names[np.argmax(prediction[0])]
    confidence=np.max(prediction[0])
    print(disease_type,confidence)

    return{
        "class":disease_type,
        "confidence":float(confidence)
    }

if __name__=="__main__":
    uvicorn.run(app,host='localhost',port=8000)

