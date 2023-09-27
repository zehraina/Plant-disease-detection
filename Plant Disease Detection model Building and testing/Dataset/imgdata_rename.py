import os
def rename_img():    
    data_dir="/Plant_Disease"
    cur_dir=os.getcwd()+data_dir
    dataset_type=os.listdir(cur_dir)
    for dir1 in dataset_type:
        path1=cur_dir+"/"+dir1
        lis1=os.listdir(path1)
        for dir2 in lis1:
            path2=path1+"/"+dir2
            lis2=os.listdir(path2)
            for dir3 in lis2:
                path3=path2+"/"+dir3
                img=os.listdir(path3)
                i=1
                print(dir2,"/",dir3)
                for img_path in img:
                    img_path=path3+"/"+img_path
                    new_path=path3+"/"+"_".join(dir3.split()[:2])+f"_{i}.jpg"
                    os.rename(img_path,new_path)
                    print(img_path,"-->",new_path)
                    i+=1
    print("Script Execution Completed")
if __name__=='__main__':
    rename_img()
