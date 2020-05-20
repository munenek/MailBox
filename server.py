from flask import Flask, request
from flask_restful import Resource, Api
from sqlalchemy import create_engine
from json import dumps
from flask_jsonpify import jsonify

db_connect = create_engine('sqlite:///letter.db')
app = Flask(__name__)
api = Api(app)

class Letter(Resource):
    def get(self):
        conn = db_connect.connect() # connect to database
        query = conn.execute("select * from letter_table") # This line performs query and returns json result
        return {'letter': [i[0] for i in query.cursor.fetchall()]} # Fetches first column that is Letter ID

    def post(self):
        conn = db_connect.connect()
        print(request.json)
        LetterName = request.json['letter']
        
        query = conn.execute("insert into letter values(null,'{0}')".format(LetterName
                             ))
        return {'status':'success'}

class Letter_Details(Resource):
    def get(self, letter_id):
        conn = db_connect.connect()
        query = conn.execute("select * from letter_table where LetterId =%d "  %int(letter_id))
        result = {'data': [dict(zip(tuple (query.keys()) ,i)) for i in query.cursor]}
        return jsonify(result)

api.add_resource(Letter, '/letter') # Route_1
api.add_resource(letter_Details, '/letter/<Letter_id>') # Route_2

if __name__ == "__main__":
     app.run(port='5002')