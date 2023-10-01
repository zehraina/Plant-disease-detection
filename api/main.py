from fastapi import FastAPI, File, UploadFile
import uvicorn
app = FastAPI()
@app.get("/ping")
async def ping():
    return "Hello FastAPI is alive"

@app.post("/predict")
async def predict(
    file: UploadFile = File(...)
):
    bytes=await

if __name__=="__main__":
    uvicorn.run(app,host='localhost',port=8000)

