import re


def processIndexFile():
    indexFile = open('index.html', 'r')
    lines = indexFile.readlines()
    indexFile.close()

    sqlRows = []
    lastItemId = None
    lastSectorLevel = 0
    parentIdArr = [None, None, None, None] # Only has 4 levels
    parentId = None
    for line in lines:
        if "<option" not in line:
            continue
        else: 
            sectorId = re.search('option value="(.*)">', line).group(1)
            contentString = re.search('>(.*)<', line).group(1)
            sectorName = contentString.replace('&nbsp;&nbsp;&nbsp;&nbsp;','').strip()
            sectorLevel = contentString.count("&nbsp;&nbsp;&nbsp;&nbsp;")
            if sectorLevel > lastSectorLevel:
                parentId = lastItemId
                parentIdArr[sectorLevel] = lastItemId
            elif sectorLevel < lastSectorLevel:
                parentId = parentIdArr[sectorLevel]
            lastItemId = sectorId
            lastSectorLevel = sectorLevel
        sqlRows.append(createSqlRow(sectorId, parentId, sectorName))
    writeLinesToFile(sqlRows)


def writeLinesToFile(sqlRows):
    sqlFile = open('src/main/resources/data.sql', 'w')
    for line in sqlRows:
        sqlFile.write(line)
        sqlFile.write("\n")
    sqlFile.close()


def createSqlRow(sectorId, parentId, contentString):
    if parentId is None:
        parentId = "null"    
    return "INSERT INTO sectors (id, parent_id, name) VALUES ({}, {}, '{}');".format(sectorId, parentId, contentString)


#  Run script
processIndexFile()
