from logging import exception
from bs4 import BeautifulSoup
from urllib.parse import urlparse
from selenium import webdriver
import asyncio
import time
import sqlite3
import asyncio
from binance.spot import Spot as Client
import httplib2 
import apiclient
from oauth2client.service_account import ServiceAccountCredentials	

class Currency:
    def __init__(self, cur = 'BTC'):
        self.currency = cur
        self.value_for_google = [
                                ]
        self.google_sheets = self.google_prepare()

    def google_prepare(self):

        self.CREDENTIAL_FILE = 'C:/Users/Valeev/Desktop/abiding-cistern-341908-0cb52c14eeaf.json'
        self.credentials = ServiceAccountCredentials.from_json_keyfile_name(self.CREDENTIAL_FILE, ['https://www.googleapis.com/auth/spreadsheets', 'https://www.googleapis.com/auth/drive'])
        httpAuth = self.credentials.authorize(httplib2.Http()) # Авторизуемся в системе
        self.service = apiclient.discovery.build('sheets', 'v4', http = httpAuth) # Выбираем работу с таблицами и 4 версию API 
        self.spreadsheetId = '1wOQnjiuXoQ34pS2gt9ExAXSIsV56pLvmuv8eckA3Qzo'

    def create_tables(self):
        connection = sqlite3.connect('C:/Users/Valeev/Desktop/Currency.db')
        cur = connection.cursor()
        cur.execute("""CREATE TABLE IF NOT EXISTS """ + 'Binance' + self.currency +"""(
            Currency TEXT,
            Prise INT ,
            URL TEXT,
            DateOfParse TEXT);
        """)
        cur.execute("""CREATE TABLE IF NOT EXISTS MOEX(
            Currency TEXT,
            Prise INT ,
            URL TEXT,
            DateOfParse TEXT);
        """)
        cur.execute("""CREATE TABLE IF NOT EXISTS """ + 'P2P_Binance' + self.currency + """buy(
            Bank TEXT,
            Currency TEXT,
            Prise INT ,
            Satus TEXT,
            URL TEXT,
            DateOfParse TEXT);
        """)
        cur.execute("""CREATE TABLE IF NOT EXISTS """ + 'P2P_Binance' + self.currency +"""sell(
            Bank TEXT,
            Currency TEXT,
            Prise INT ,
            Satus TEXT,
            URL TEXT,
            DateOfParse TEXT);
        """)
        cur.execute("""CREATE TABLE IF NOT EXISTS LocalBitcoinsbuy(
            Bank TEXT,
            Currency TEXT,
            Prise INT ,
            Satus TEXT,
            URL TEXT,
            DateOfParse TEXT);
        """)
        cur.execute("""CREATE TABLE IF NOT EXISTS LocalBitcoinssell(
            Bank TEXT,
            Currency TEXT,
            Prise INT ,
            Satus TEXT,
            URL TEXT,
            DateOfParse TEXT);
        """)
        connection.commit()
        connection.close()

    def write_info(self, name, value, status = '', fiat = 'RUB' ):
        connection = sqlite3.connect('C:/Users/Valeev/Desktop/Currency.db')
        cur = connection.cursor()
        if name == 'MOEX' :
            cur.execute('INSERT INTO MOEX VALUES(?,?,?,?)', value)
        if name == 'Binance'+ self.currency:
            cur.execute('INSERT INTO BinanceBTC VALUES(?,?,?,?)',  value)
        if name == 'P2P_Binance' + self.currency +'sell' :
             cur.execute('INSERT INTO P2P_BinanceBTCsell VALUES(?,?,?,?,?,?)',value)
        if name == 'P2P_Binance' + self.currency + 'buy':
             cur.execute('INSERT INTO P2P_BinanceBTCbuy VALUES(?,?,?,?,?,?)',value)
        if name == 'LocalBitcoins' + 'Sell' :
             cur.execute('INSERT INTO LocalBitcoinssell VALUES(?,?,?,?,?,?)', value)
        if name == 'LocalBitcoins' + 'Buy' :
             cur.execute('INSERT INTO LocalBitcoinsbuy VALUES(?,?,?,?,?,?)', value)
        connection.commit()
        connection.close()

    def get_currency_from_Binance(self):
        try:
            spot_client = Client()

            prise = spot_client.ticker_24hr(self.currency + 'RUB')['lastPrice']
            value = ('BTC',prise, '' , time.time())

            self.write_info('Binance'+self.currency, value)
            print(prise)
        except:
            exception('cannot get price from binance')
              
    def get_USD_RUB(self):
        try:
            url = "https://www.moex.com/ru/issue/USD000000TOD/CETS"
            response = webdriver.Firefox()
            response.get(url)
            
            '''await asyncio.sleep(5)'''

            source = response.page_source

            soup = BeautifulSoup(source, 'lxml')
            div = soup.find('div', class_='digest')
            prise = div.find('li', class_ = 'last')

            value = ['USDRUB', prise.get_text().strip(), urlparse(url).netloc, time.time()]
            self.value_for_google.append(value)
            self.write_info('MOEX', value)
            response.close()
        except :
            print('error: cannot parse USD')

    def get_usd(self):
        connection = sqlite3.connect('C:/Users/Valeev/Desktop/Currency.db')
        cur =  connection.cursor()
        cur.execute('SELECT * FROM MOEX ORDER BY DateOfParse DESC')
        ar = cur.fetchone()
        self.value_for_google.append(['USD ', ' ' ,str(ar[1]) ])

    async def get_from_p2p_binance(self, fiat, status):
        try:
            # We also need to write the status bisides all things which were mentioned below

            url = "https://p2p.binance.com/ru/trade/" + status +"/" +self.currency+"?fiat="+ fiat +"&payment=ALL"

            response = webdriver.Firefox()
            response.get(url)

            await asyncio.sleep(5)

            bank =''


            source = response.page_source
            soup = BeautifulSoup(source,'lxml' )
            large_div = soup.find('div', class_ = 'css-16g55fu')
            div = large_div.find_all('div', class_ = 'css-vurnku')
            for n,i in enumerate(div, start = 1):
                k = i.find_all('div', class_ = 'css-tsk0hl')
                for h,g in enumerate(k, start = 1):
                    prise = g.find('div', class_ = 'css-1m1f8hn')

                    """name = g.find('a', id = id_for_user_name)"""

                    banks = g.find_all('div', class_ = 'css-1xsacww')
                    for j in range(len(banks)):
                        if banks[j].get_text().strip() not in bank:
                            bank +=  banks[j].get_text().strip() + ' '
                        else:
                            pass
                    value = (bank, self.currency, prise.get_text().strip()+'RUB', status,  urlparse(url).netloc, time.time() )
                    
                    bank =''

                    self.write_info( 'P2P_Binance' + self.currency + status , value, status, fiat)
            response.close()
        except:
            print('error: cannot parse p2p')
    
    async def get_currency_from_local_bitcoin(self):
        try:
            response = webdriver.Firefox()
            url = 'https://localbitcoins.net/?%3C!--'
            response.get(url)

            await asyncio.sleep(5)

            source = response.page_source

            name = ''
            prise = ''

            soup = BeautifulSoup(source, 'lxml')

            div =soup.find('div', id = 'frontpage-location-listing')
            table = div.find_all('table')
            for d,h in enumerate(table,start = 1):
                tr = h.find_all('tr')
                for n,i in enumerate(tr, start = 0):
                    name = i.find('td',class_="column-user")
                    if name != None:
                        name_of_person = name.find('a')
                    prise = i.find('td',class_ = 'column-price')
                    bank = i.find('td', class_= False, id = False)
                    status = i.find('td',class_ = 'column-button')
                    if name != None and bank != None :
                        value = (bank.get_text().replace(' ','').replace('\n',''), self.currency, prise.get_text().replace(' ','').replace('\n',''), status.get_text().replace(' ','').replace('\n','') ,urlparse(url).netloc, time.time()  )
                        self.write_info('LocalBitcoins' + status.get_text().replace(' ','').replace('\n',''), value )
                    else: 
                        pass
            response.close()
        except:
            print('error: cannot parse localBitcoins')

    def get_lib_of_sell_or_buy(self, smth, cur):
        cur.execute(smth)
        ar = cur.fetchmany(6)
        black_list =['Система','быстры...','баланс','Фиатный','-', 'Перевод', 'по','карте','Наличный','расчет','RUB']
        lib_of_info = {}

        banks_list = []

        for i in ar:#going through each row
            for n,j in enumerate(i, start = 1):#going through each table
                if n == 1:#we need name of a bank so we taking first table of the row
                    bank = j
                    banks_list = bank.split()
                    if len(banks_list) != 0:
                        for p in banks_list:#cheking each bank and excluding names that we don't need
                            if p not in black_list and 'Cashdeposit' not in p :
                                lib_of_info.update({p: []})
                    elif 'Cashdeposit' not in bank:#if we have only one value in bank 
                        lib_of_info.update({bank: []})
                else:#fulling our library of banks
                    if len(banks_list) != 0:
                        for p in banks_list:
                            if p not in black_list and 'Cashdeposit' not in p :
                                lib_of_info[p].append(j)
                    elif 'Cashdeposit' not in bank:
                            lib_of_info[bank].append(j)
            banks_list.clear()   
        return lib_of_info 

    def get_info_from_db_LocalBitcoins_and_P2P_Binance(self,site, smthbuy, smthsell):
        bank = ''
        lib_of_info_sell = {}
        lib_of_info_buy = {}
        connection = sqlite3.connect('C:/Users/Valeev/Desktop/Currency.db')
        cur =  connection.cursor()

        lib_of_info_sell =  self.get_lib_of_sell_or_buy(smthsell, cur)
    
        lib_of_info_buy = self.get_lib_of_sell_or_buy(smthbuy, cur)
        

        keys_to_delete =[]

        # cheking if we have simillar banks in buy and sell
        for i in lib_of_info_buy.keys():
            for j in lib_of_info_sell.keys():
                if i == j:
                    bank = i
                    self.value_for_google.append([site, bank, lib_of_info_buy[i][1], lib_of_info_sell[j][1]])
                    keys_to_delete.append(i)

        for i in keys_to_delete:        
            del lib_of_info_sell[i]
            del lib_of_info_buy[i]
        keys_to_delete.clear()

        for i in lib_of_info_buy.keys():
            bank = i 
            self.value_for_google.append([site, bank , lib_of_info_buy[i][1] , ' '])
        for j in lib_of_info_sell.keys():
            bank = j
            self.value_for_google.append([site, bank,'', lib_of_info_sell[j][1]])


    def get_info_from_Binance(self):
        connection = sqlite3.connect('C:/Users/Valeev/Desktop/Currency.db')
        cur =  connection.cursor()
        cur.execute('SELECT * FROM BinanceBTC ORDER BY DateOfParse DESC')
        ar = cur.fetchone()
        self.value_for_google.append(['binance ', ' ' ,str(ar[1]) + ' RUB', str(ar[1]) + ' RUB' ])

    def send_info_to_google_sheets(self):
        resultClear = self.service.spreadsheets( ).values( ).clear( spreadsheetId = self.spreadsheetId, range="Лист номер один!B2:F50",
                                                       body={} ).execute( )
        self.service.spreadsheets().values().batchUpdate(spreadsheetId = self.spreadsheetId, body = {
            "valueInputOption": "USER_ENTERED", # Данные воспринимаются, как вводимые пользователем (считается значение формул)
            "data": [
                {"range": "Лист номер один!B2:F50",
                "majorDimension": "ROWS",     # Сначала заполнять строки, затем столбцы
                "values": self.value_for_google}
            ]
        }).execute()
        self.value_for_google.clear()

    def google_talbe_is_filled(self):
        results = self.service.spreadsheets().values().batchGet(spreadsheetId = self.spreadsheetId, 
                                                                ranges = ["Лист номер один!G1:M50"],
                                                                valueRenderOption = 'FORMATTED_VALUE',
                                                                dateTimeRenderOption = 'FORMATTED_STRING').execute()

        if 'values' in results['valueRanges'][0].keys():
            resultClear = self.service.spreadsheets( ).values( ).clear( spreadsheetId = self.spreadsheetId, range="Лист номер один!G1:M50",
                                                       body={} ).execute( )
            return True
def for_Bot():
    cur = Currency()
    cur.get_USD_RUB()
    cur.get_currency_from_Binance()

def main():
    curnc = Currency()
    curnc.create_tables()

    '''ioloop = asyncio.get_event_loop()
    tasks = [
                    ioloop.create_task(curnc.get_from_p2p_binance('RUB','sell')),
                    ioloop.create_task(curnc.get_from_p2p_binance('RUB','buy')),
                    ioloop.create_task(curnc.get_currency_from_local_bitcoin()),              
        ]
    ioloop.run_until_complete(asyncio.wait(tasks))
    ioloop.close()'''
    curnc.get_info_from_Binance()
    curnc.get_usd()
    '''curnc.get_info_from_db_LocalBitcoins_and_P2P_Binance('localbitcoins','SELECT * FROM LocalBitcoinsbuy ORDER BY DateOfParse DESC','SELECT * FROM LocalBitcoinssell ORDER BY DateOfParse DESC')
    curnc.get_info_from_db_LocalBitcoins_and_P2P_Binance('p2pbinance','SELECT * FROM P2P_BinanceBTCbuy ORDER BY DateOfParse DESC','SELECT * FROM P2P_BinanceBTCsell ORDER BY DateOfParse DESC')'''
    return curnc.value_for_google
print(main())

