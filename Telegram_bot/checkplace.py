from unicodedata import name
from bs4 import BeautifulSoup
from selenium import webdriver

names = {
    '09.03.01':'https://ssau.ru/ratings/bakalavr/12',
    '12.03.04':'https://ssau.ru/ratings/bakalavr/22?priority=false#id1734594338231424278',
    '03.03.01':'https://ssau.ru/ratings/bakalavr/6?priority=false#id1734593943759230230',
    '12.03.05':'https://ssau.ru/ratings/bakalavr/23?priority=false#id1734594358415463702'
}
matchingIds  = {
    'https://ssau.ru/ratings/bakalavr/12':'id1734591119047529750',
    'https://ssau.ru/ratings/bakalavr/22?priority=false#id1734594338231424278':'id1734594338231424278',
    'https://ssau.ru/ratings/bakalavr/6?priority=false#id1734593943759230230':'id1734593943759230230',
    'https://ssau.ru/ratings/bakalavr/23?priority=false#id1734594358415463702':'id1734594358415463702'
}

def checkInSite(urls,namesOfFaculty, studentInfo = '199-639-662-61'):
    if len(urls) != 0:
        listToReturn = []
        for k,url in enumerate(urls,start = 0):
            response = webdriver.Firefox()
            response.get(url)

            source = response.page_source
            bs = BeautifulSoup(source, 'lxml')
            n = bs.find('div', class_ ='subtitle1-text text-white').text.split()[-1]
            div = bs.find('div',id = matchingIds[url])
            table = div.find('tbody')
            for student in table:
                if studentInfo in student.text:
                    listToReturn.append(namesOfFaculty[k] + ': Вы на ' + student.text[:4] + 'месте. Всего мест - ' + n)
                    break
            response.close()
        return listToReturn
print(checkInSite(urls = [names['09.03.01'],names['12.03.04']], namesOfFaculty = ['09.03.01','12.03.04']))