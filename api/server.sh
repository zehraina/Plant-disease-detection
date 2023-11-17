#!/bin/bash
python /home/fansan/pagekite.py 8000 fansan.pagekite.me 
source /home/fansan/anaconda3/etc/profile.d/conda.sh
conda activate base
python main.py
