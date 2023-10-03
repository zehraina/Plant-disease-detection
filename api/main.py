from fastapi import FastAPI, File, UploadFile
import numpy as np
from io import BytesIO
from PIL import Image
import uvicorn
app = FastAPI()
@app.get("/ping")
async def ping():
    return "Hello FastAPI is alive"


def read_file_as_image()->np.ndarray:
    Image.open(BytesIO(data)) 
@app.post("/predict")
async def predict(
    file: UploadFile = File(...)
):
    image=read_file_as_image(await file.read())
    return

if __name__=="__main__":
    uvicorn.run(app,host='localhost',port=8000)

