# -*- coding: utf-8 -*-
"""
Created on Tue Jun 15 22:28:43 2021

@author: 1USER
"""

import pymysql
import pandas as pd

class Online_DB_Handler:
    '''
    '''
    
    def __init__(self):
        self.__host = "localhost"
        self.__user = "root"
        self.__password = "CNS_Pass4975"
        self.__database = "businessdatabaseonline"
        return
    
    def read_database(self, table_name):
        
        sql = "SELECT * FROM " + table_name
        
        db = pymysql.connect(
            host=self.__host,user=self.__user,password=self.__password,
            database=self.__database)
        
        cursor = db.cursor() 
        
        try:
           cursor.execute(sql)
           data = pd.DataFrame(cursor.fetchall())
           db.close()
           return data
        except:
           print ("Error: unable to fetch data")
           db.close()
           return data