from fastapi import FastAPI, File, UploadFile
import uvicorn
import numpy as np
from io import BytesIO
from PIL import Image
import tensorflow as tf
import requests
import cv2
app = FastAPI()

OOD_MODEL = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/OOD/ood6")

MODEL_0 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/0/0_Potato_mobileNet")
#MODEL_1 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/1/1_Tomato_mobileNet")
MODEL_2 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/2/2_Corn_mobileNet")
MODEL_3 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/3/3_Apple_mobileNet")
MODEL_4 = tf.keras.models.load_model("../model_building_and_testing/models/saved_models/4/4_Grapes_mobileNet")
# endpoint = "http://localhost:8502/v1/models/potatoes_model:predict"
CLASS_NAMES2 = ["Early Blight", "Late Blight", "Healthy","Junk"]

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
        file: UploadFile = File(...)):

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
    
    predictions = MODEL_0.predict(img_batch)
    
    predicted_class = np.argmax(predictions[0])
    class_name=CLASS_NAMES2[predicted_class]
     
    confidence = np.max(predictions[0])*100

    print(class_name, confidence)  
    
    return {
        'class': class_name,
        'confidence': float(confidence)
    }


if __name__ == "__main__":
    uvicorn.run(app, host='localhost', port=8000)
