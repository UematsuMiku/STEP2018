
# coding: utf-8

# In[7]:


def readNumber(line, index): #数字のみの並びを読み取る
    number = 0
    flag = 0
    keta = 1
    while index < len(line) and (line[index].isdigit() or line[index] == '.'):
        if flag == 1:
            keta *= 0.1
        if line[index] == '.':
            flag = 1
        else:
            number = number * 10 + int(line[index])
        index += 1
    # print(number * keta)
    token = {'type': 'NUMBER', 'number': number * keta}
    return token, index


def readPlus(line, index):
    token = {'type': 'PLUS'}
    return token, index + 1

def readMinus(line, index):
    token = {'type': 'MINUS'}
    return token, index + 1

def readMultiply(line, index):
    token = {'type': 'MULTIPLY'}
    return token, index + 1

def readDivide(line, index):
    token = {'type': 'DIVIDE'}
    return token, index + 1




def tokenize(line): #演算子に処理できないものが含まれていないか判定
    tokens = []
    index = 0
    while index < len(line):
        if line[index].isdigit():
            (token, index) = readNumber(line, index)
        elif line[index] == '+':
            (token, index) = readPlus(line, index)
        elif line[index] == '-':
            (token, index) = readMinus(line, index)
        elif line[index] == '*':
            (token, index) = readMultiply(line, index)
        elif line[index] == '/':
            (token, index) = readDivide(line, index)      
        else:
            print ('Invalid character found: ' + line[index])
            print()
            return []
            #+-*/と数字以外が含まれている時エラー
            #(+-*/と数字の並び順は考慮しない)
        tokens.append(token)

    return tokens


def check_seq(tokens): #演算子の並び順を判定
    if tokens[0]['type'] != 'NUMBER' and tokens[0]['type'] != 'PLUS' and tokens[0]['type'] != 'MINUS': #最初は+か-か数字
        print('First character is invalid.')
        return 1
    index = 1
    while index < len(tokens):  #演算子が２つ並ぶとき，２つ目は+-なら許す*/は許さない
        if tokens[index]['type'] != 'NUMBER' and tokens[index]['type']!= 'PLUS' and tokens[index]['type']!= 'MINUS': 
            if tokens[index-1]['type'] != 'NUMBER':                                                                  
                print(tokens[index-1]['type'] + " " + tokens[index]['type'] + " is appeard.")
                return 1             
        index = index+1
        
    return 0

def process_continuity(tokens): #演算子が２つ続く場合の処理（*-3などとできる）
    index = 2
    while index < len(tokens):
        if tokens[index]['type'] == 'NUMBER':
            if tokens[index-2]['type'] != 'NUMBER': #+-*/のどれか
                if tokens[index-1]['type'] == 'PLUS':
                    tokens.insert(index-1,{'type':'NUMBER','number':tokens[index]['number'] })
                elif tokens[index-1]['type'] == 'MINUS':
                    tokens.insert(index-1,{'type':'NUMBER','number':(-1)*tokens[index]['number'] })
                tokens.pop(index)
                tokens.pop(index)
        index +=1
    return tokens


def evaluate(tokens):
    answer = 0
    keep = 1
    index = 1
    if tokens[0]['type'] == 'NUMBER':
        tokens.insert(0, {'type': 'PLUS'}) # Insert a dummy '+' token 最初に+を入れる

    
    #*と/の処理
    while index < len(tokens):
        if tokens[index]['type'] == 'NUMBER':
            if tokens[index - 1]['type'] == 'MULTIPLY':
                tokens.insert(index - 2,{'type': 'NUMBER', 'number': keep * tokens[index]['number']}) #計算結果を挿入
                tokens.pop(index-1)                                                                   #数字と演算子を削除
                tokens.pop(index-1)                                                                   #4+2*3+...→4+6+...とする
                tokens.pop(index-1)
                index -= 2  #*の計算結果までindexを動かす
            elif tokens[index - 1]['type'] == 'DIVIDE':
                if tokens[index]['number'] == 0:                         #0で割るエラーを防ぐ
                         print ('Divided by 0 in index' + str(index))
                         return "/0"
                tokens.insert(index - 2,{'type': 'NUMBER', 'number': keep / tokens[index]['number']})
                tokens.pop(index-1)
                tokens.pop(index-1)
                tokens.pop(index-1)
                index -= 2
            elif tokens[index - 1]['type'] == 'PLUS' or tokens[index-1]['type'] == 'MINUS':  
                keep = tokens[index]['number']  #次に*か/が来たときのためにキープ +2*3→2をキープ
                index += 2
            else:
                print('Invalid syntax 1' + str(index))
        else:
            print ('Invalid syntax 1 ' + str(index))
        
    #+と-の処理
    index = 1
    while index < len(tokens):
        if tokens[index]['type'] == 'NUMBER':
            if tokens[index - 1]['type'] == 'PLUS':
                answer += tokens[index]['number']
            elif tokens[index - 1]['type'] == 'MINUS':
                answer -= tokens[index]['number']
            else:
                print ('Invalid syntax 2 ' + str(index))
        index += 2
    return answer

    
    
def test(line, expectedAnswer):
    print("input: " + line)
    tokens = tokenize(line)
    if tokens == []:  #入力に処理できない文字が含まれているとき
        print()
        return
    if check_seq(tokens) == 1:  #文字の並び順がおかしいとき
        print()
        return
    tokens = process_continuity(tokens) # *-3→*(-3)となるようにtokensを変形（演算子が２つ続くときの処理）
    actualAnswer = evaluate(tokens)
    if actualAnswer == "/0":
        print()
    elif abs(actualAnswer - expectedAnswer) < 1e-8:
        print ("PASS! (%s = %f)" % (line, expectedAnswer))
        print()
    else:
        print ("FAIL! (%s should be %f but was %f)" % (line, expectedAnswer, actualAnswer))
        print()
    # Add more tests to this function :)



def runTest():
    print ("==== Test started! ====")
    test("+5*-4",-20)
    test("5-*4",0)
    test("-5",-5)
    test("1+2", 3)
    test("1.0+2.1-3", 0.1)
    test("0.3*10*-5",-15)
    test("-4+2.5*3+1",4.5)
    test("2/0",100)
    test("3+30/5/2-4",2)
    print ("==== Test finished! ====\n")

runTest()
while True:
    print ('> '),
    line = input()
    tokens = tokenize(line)
    if tokens == []:  #入力に処理できない文字が含まれているとき
        print()
        exit()
    if check_seq(tokens) == 1:  #文字の並び順がおかしいとき
        print()
        exit()
    tokens = process_continuity(tokens) # *-3→*(-3)となるようにtokensを変形（演算子が２つ続くときの処理）
    answer = evaluate(tokens)
    print ("answer = %f\n" % answer)
        

