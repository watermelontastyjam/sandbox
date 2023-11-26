from telebot import types
import telebot
import re
import ParsingProject
import sqlite3
from random import  randint
import checkplace

bot = telebot.TeleBot('5024807263:AAG9oI33XcNtb_lpuphE4w4YOmM_SvgeiG0')

availableCities1 = {'а' :['Астрахань'] ,'б': [],'в':[], 'г':['Гладиатор'],'д':['Донецк'],
                'е':['Ефрейтор'],'ж':['Жопинск'],'з':['Заднеприводск'],'и':['ИдешьНаХуй'],'к':['Колыма'],
                'л':['Людвиг'],'м':['Мама'],'н':['Неонацист'],'о':['Опаньки'],'п':['Папа'],'р':['Ростов'],
                'с':['Сукинск'],'т':['Тамада'],'у':['УСука'],'ф':['Фарит'],'х':['ХайчЕбучий'],'ц':['Цаца'],'ч':['Чуханово'],
                'ш':['ШоколадНеВиноват'],'щ':['Щавель'],'э':['Эжжибля'],'ю':['Юшка'],'я':['ЯшаЛава']}
availableCities2 = {'А' :[] ,'Б': [],'В':[], 'Г':['Гладиатор'],'Д':['Донецк'],
                'Е':['Ефрейтор'],'Ж':['Жопинск'],'З':['Заднеприводск'],'И':['ИдешьНаХуй'],'К':['Колыма'],
                'Л':['Людвиг'],'М':['Мама'],'Н':['Неонацист'],'О':['Опаньки'],'П':['Папа'],'Р':['Ростов'],
                'С':['Сукинск'],'Т':['Тамада'],'У':['УСука'],'Ф':['Фарит'],'Х':['ХайчЕбучий'],'Ц':['Цаца'],'Ч':['Чуханово'],
                'Ш':['ШоколадНеВиноват'],'Щ':['Щавель'],'Э':['ЭжжиБля'],'Ю':['Юшка'],'Я':['ЯшаЛава']}
list_of_greetins = ['Хай','хай','hi','Hi','HI','привет','Привет','Здорова','здорова','приветик','Приветик','приветики','Приветики',
                    'привет бот','Привет бот','привет Бот','Привет, Бот','привет, бот','Привет, бот','привет, Бот','Привет, Бот','ghbdtn','Ghbdtn','{fq','[fq']
unavaibleCities = []
numbers = [i for i in range(1,101)]
name1 = {}
name = ''
surname = ''
age = 0
amount_of_fails = 0
info_abt_one_pers = []
user_id = 0
start = False
stresses = []
mistakes = ''
snils = ''
        
def makeItNumber(text):
    
    for a in range(-1000, 1000):
        numbers.append(a)
    d = len(text)
    numb = ' ' 
    n = 0
    abc = []
    for i in range(d):
        if text[i] != ' ' :
            numb += text[i]
        if text[i] == ' ' or i == d-1:
            abc.append(numb)
            numb = ' '
    return abc
            
def equation(numb):
    a = float(numb[0])
    b = float(numb[1])
    c = float(numb[2])

    D = b**2 - 4 * a *c
    if D < 0 :
        return 'решений нет'
    elif D == 0:
        return [-b/2*a, -b/2*a]
    elif D > 0:
        return [(-b + D**0.5)/(2*a), (-b - D**0.5)/(2*a)]

def results_of_the_equation(message):
    try:
        numb = makeItNumber(message.text)
        if int(numb[0]) in numbers  :
            for i in range(len(numb)):
                bot.send_message(message.chat.id, text = (numb[i]))
            result = equation(numb)
            if type(result) == str:
                bot.send_message(message.chat.id, text = 'Ответ:' + result )
            elif type(result) == list:
                bot.send_message(message.chat.id, text = 'Ответ: ' + str(result[0]) + ' , ' + str(result[1]) )
    except:
        pass

def get_name(message):
    global name
    name = message.text
    bot.send_message(message.chat.id, "Введи свою фамилию")
    bot.register_next_step_handler(message, get_surname)

def get_surname(message):
    global surname
    surname = message.text
    bot.send_message(message.chat.id, "Введи свой возраст")
    bot.register_next_step_handler(message, get_age)

def get_age(message):
    global age
    age = message.text
    if int(age) in numbers:
        try:
            age = int(message.text)
        except Exception:
            bot.send_message(message.chat.id,'Цифрами пожалуйста')
            bot.register_next_step_handler(message, get_age)
        keyboard = types.InlineKeyboardMarkup() #наша клавиатура
        key_yes = types.InlineKeyboardButton(text='Да', callback_data='yes') #кнопка «Да»
        keyboard.add(key_yes) #добавляем кнопку в клавиатуру
        key_no= types.InlineKeyboardButton(text='Нет', callback_data='no')
        keyboard.add(key_no)
        question = 'Тебе '+str(age)+' лет, тебя зовут '+name+' '+surname+'?'
        bot.send_message(message.chat.id, text=question, reply_markup=keyboard)
        write_info(name, surname, age, message)

def response():
    phrases = ['Да','Нет', 'Ага', 'Согласен', 'Похуй','Что-то типа того','Наверное, хз','Чет хуйня какая-то','Хуйню несешь']
    return phrases[randint(0,len(phrases) - 1)]        

def read_cities_from_file():
    list_of_cities = []
    russian_alphabet = ['А','Б','В','Г','Д','Е','Ж','З','И','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Э','Ю','Я',] 
    lowercase_russian_alphabet =  {'А' : 'а','Б': 'б','В': 'в','Г': 'г','Д': 'д','Е': 'е','Ж': 'ж','З': 'з','И': 'и','К': 'к','Л': 'л',
                                    'М': 'м','Н': 'н','О': 'о','П': 'п','Р': 'р','С': 'с','Т': 'т','У': 'у','Ф': 'ф','Х': 'х','Ц': 'ц',
                                    'Ч': 'ч','Ш': 'ш','Щ': 'щ','Э': 'э','Ю': 'ю','Я': 'я'}
    with open ('C:/Users/Valeev/Desktop/list_of_cities.txt', encoding="utf-8" ,mode ='r') as file:
        list_of_cities = file.readlines()
    for k in range(len(list_of_cities)):
        for n in range(len(russian_alphabet)):
            if list_of_cities[k][0] == russian_alphabet[n]:
                availableCities1[lowercase_russian_alphabet[russian_alphabet[n]]].append(list_of_cities[k])
                availableCities2[russian_alphabet[n]].append(list_of_cities[k])
           
def game_cities(message):
    global amount_of_fails, availableCities1, unavaibleCities, availableCities2 
    try:

        if message.text == '/unava':
            for i in range(len(unavaibleCities)):
                bot.send_message(message.chat.id, unavaibleCities[i])
        
        elif(message.text +"\n" in availableCities2[message.text[0]]) and amount_of_fails <= 3 and not(message.text + "\n" in unavaibleCities) : #Cheking if the word that was written by player doesn't contains in unavailable cities
            unavaibleCities.append(message.text + "\n")
            for k in range(len(message.text)):
                if k == len(message.text)-1 and message.text[k] != 'ь' and message.text[k] != 'ы'and message.text[k] != 'ъ'and message.text[k] != 'ё'and message.text[k] != 'й' : #Bot's response
                    city = availableCities1[message.text[k]][randint(0, len(availableCities1[message.text[k]]))]
                    while city in unavaibleCities:
                        city = availableCities1[message.text[k]][randint(0, len(availableCities1[message.text[k]]))]
                    bot.send_message(message.chat.id, text = city)
                    unavaibleCities.append(city) 
                    bot.register_next_step_handler(message, game_cities)
                elif k == len(message.text)-1 : #Cheking if the word ends by 'ь,ъ,й and etc'
                    city = availableCities1[message.text[k-1]][randint(0, len(availableCities1[message.text[k-1]]))]
                    while city in unavaibleCities:
                        city = availableCities1[message.text[k-1]][randint(0, len(availableCities1[message.text[k-1]]))]
                    unavaibleCities.append(city) 
                    bot.send_message(message.chat.id, text = city)
                    bot.register_next_step_handler(message, game_cities)

        elif (message.text +"\n" in availableCities2[message.text[0]]) and amount_of_fails <= 3 and message.text + "\n" in unavaibleCities :
            bot.send_message(message.chat.id, "Вы уже использовали этот город, подумайте чуть-чуть и напишите другой")
            bot.register_next_step_handler(message, game_cities)
        elif message.text[0] != '/' and amount_of_fails <= 3:
            amount_of_fails += 1
            bot.send_message(message.chat.id, text = 'Такого города нет')
            bot.register_next_step_handler(message, game_cities)
        if amount_of_fails > 3:
            bot.send_message(message.chat.id, text="Задолбал, ты уже много раз сделал ошибки, иди на все 4 стороны, если не хочешь нормально играть ")
            bot.register_next_step_handler(message, get_text_messages)
    except Exception:
        bot.register_next_step_handler(message, game_cities)

@bot.callback_query_handler(func=lambda call: True)
def callback_worker(call):
    if call.data == "yes": 
        global name, surname, age
        bot.send_message(call.message.chat.id, 'Принял, вы зарегестрированы)')
        
    elif call.data == "no":
        bot.send_message(call.message.chat.id, 'Ну, ладно ')
    else:
        for i in checkplace.names.keys():
            if call.date == i:
                bot.register_next_step_handler(call.message ,placeInUniversity, i)
                break
    
    
def write_info(name, surname, age, message):
    connection = sqlite3.connect(r'D:\telegram bot\base.db')
    cursor  = connection.cursor()
    cursor.execute("""CREATE TABLE IF NOT EXISTS """ + "T" + str(message.from_user.id) + """ (
        Name TEXT,
        SURNAME TEXT,
        AGE TEXT\
    );""")
    connection.commit()
    value = (name,surname, age)
    cursor.execute('INSERT INTO ' + "T" + str(message.from_user.id) + ' VALUES(?,?,?)',value)
    connection.commit()
    connection.close()

def pull_users_name(message):
    connection = sqlite3.connect(r'D:\telegrambot\base.db')
    cursor = connection.cursor()
    cursor.execute('SELECT * FROM T' + str(message.from_user.id))
    ar = cursor.fetchone()
    name1.update({message.from_user.id: ar})
    connection.close()

def read_sresses():
    global stresses
    if len(stresses) == 0:
        with open(r'D:\telegrambot\udarenie.txt', encoding='utf-8') as f:
            for i in f.readlines():
                stresses.append(i.strip())

def game_stress(message,word):
    global mistakes

    read_sresses()
    if message.text == '/end':
        if len(mistakes) == 0:
            bot.send_message(message.chat.id,'Ошибок нет')
            bot.register_next_step_handler(message, get_text_messages)
        else:
            bot.send_message(message.chat.id, 'Ошибки(a): ' + mistakes)
            bot.register_next_step_handler(message, get_text_messages)
    elif message.text[1:] == word[1:] and message.text[0].lower() == word[0]: 
        bot.send_message(message.chat.id, 'Правильно!')
        word = stresses[randint(0, len(stresses)-1)]
        bot.send_message(message.chat.id, word.lower())
        bot.register_next_step_handler(message, callback = game_stress, word = word)
    else:
        mistakes += ' ' + word
        bot.send_message(message.chat.id, 'Неправильно! ' + word)
        word = stresses[randint(0, len(stresses)-1)]
        bot.send_message(message.chat.id, word.lower())
        bot.register_next_step_handler(message, callback = game_stress, word = word)

def check_stress(message):
    read_sresses()
    lower_words = [i.lower() for i in stresses]
    if message.text in lower_words:
        bot.send_message(message.chat.id, stresses[lower_words.index(message.text)] )
    else:
        bot.send_message(message.chat.id, 'В моем списке нет такого')

def getFuculties(message):
    global snils,listOfFuculties
    snils = message.text.strip()
    keyboard = types.InlineKeyboardMarkup()
    listOfKeys = []
    for key in checkplace.names.keys():
        listOfKeys.append(types.InlineKeyboardButton(text = key, callback_data = key)) 
    for i in listOfKeys:
        keyboard.add(i)

    listOfFuculties = message.text.strip()
    bot.register_next_step_handler(message,placeInUniversity)
    
def placeInUniversity(message):
    global snils,listOfFuculties
    snils = message.text.strip()
    listOfFucults = []
    for i in listOfFuculties:
        listOfFucults.append(checkplace.names[i])
    for i in checkplace.checkInSite(listOfFucults,listOfFuculties, snils):
        bot.send_message(message.chat.id, i)
    bot.register_next_step_handler(message, get_text_messages)

@bot.message_handler(content_types=['text'])
@bot.message_handler(content_types=['text','document','audio'])
def get_text_messages(message):
    global unavaibleCities, start

    pull_users_name(message)

    if '/bot' == message.text and start == False:
        bot.send_message(message.chat.id,'Oh. Yes, sir')
        start = True

    if start:
        if name1[message.from_user.id][0] != "":
            if  message.text in list_of_greetins or message.text == "/help":
                bot.send_message(message.chat.id, "Привет, "+ name1[message.from_user.id][0] + ''', ты можешь решить с моей помощью квадратное уравнение, введя "/sqr", или ты можешь поиграть со мной в города, введя "/ci", также можешь задать мне вопрос, написав " Антон,*вопрос*? ", я отвечу:), и конечно же можешь узнать курс биткоина или доллара на данный момент, просто надо написать "/BTC" или "/USD" соответственно , хочешь проверить свои знания орфоэпии напиши "/stress", или если хочешь проверить ударение напиши "/checkstress" ''')
                ParsingProject.for_Bot()
            if message.text == "/sqr":
                bot.send_message(message.chat.id, "Введи a, b, c для уравнения a * X^2 + b * X + c = 0")
                bot.register_next_step_handler(message, results_of_the_equation)
            if message.text == "/ci":
                global amount_of_fails
                bot.send_message(message.chat.id, "Ну, давай поиграем в города")
                read_cities_from_file()
                unavaibleCities.clear()
                bot.send_message(message.chat.id, text= "Начинай")
                bot.send_message(message.chat.id, text= "Только пиши города с большой буквы")
                bot.register_next_step_handler(message, game_cities)
                amount_of_fails = 0
            if re.match(r'Антон*', message.text) and '?' in message.text:
                to_send = response()
                bot.send_message(message.chat.id, to_send)
                if to_send == 'Хуйню несешь' or to_send == 'Похуй':
                    bot.send_message(message.chat.id,'😎')
            if message.text == '/BTC':
                value =  ParsingProject.main()
                bot.send_message(message.chat.id, 'Bitcoin currently costs ' + value[0][3])
            if message.text == '/USD':
                value =  ParsingProject.main()
                bot.send_message(message.chat.id, value[1][0]+' currently costs '+ value[1][2][:5])   
            if message.text == '/stress':
                read_sresses()
                word = stresses[randint(0, len(stresses)-1)]
                bot.send_message(message.chat.id, word.lower())
                bot.register_next_step_handler(message, game_stress, word = word)
            if message.text == '/checkstress':
                bot.send_message(message.chat.id, 'Напиши слово, которое хочешь проверить (не все слова есть)')
                bot.register_next_step_handler(message, check_stress)
            if message.text == '/checkplace':
                bot.send_message(message.chat.id, 'Введи свой СНИЛС (Работает только с Самарским университетом)')
                bot.register_next_step_handler(message, getFuculties)
            name1.clear()
        elif message.text == "/reg":
            bot.send_message(message.chat.id, "Скажи свое имя")
            bot.register_next_step_handler(message, get_name)
        elif re.match(r'Антон*', message.text) and '?' in message.text:
            to_send = response()
            bot.send_message(message.chat.id, to_send)
            if to_send == 'Хуйню несешь' or to_send == 'Похуй':
                bot.send_message(message.chat.id,'😎') 
        else:
            bot.send_message(message.chat.id, "Тебе нужно зарегестрироваться. Для этого тебе нужно ввести '/reg'")

    if message.text == 'Оффнись' and start:
        bot.send_message(message.chat.id, "😢")
        start = False   
        
bot.polling(none_stop=True, interval=1)