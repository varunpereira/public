git clone <link>
cd <repo>
git checkout <branch>
<drag local files into folder>
git add -A
git commit -m "message"
git push origin <branch>
git status
git remote add origin


(inside myfirstapp (in frontend folder) open cli:)
npm install
npm start

(quickly add data to H2 DB: <http://localhost:1002/h2-console/> replace 1002 with port number in application.properties)
INSERT INTO Book (name, author, isbn, category, price, seller, stocklevel, contenturl, coverurl, newbook, loanedbook)
VALUES ('Harry and Me', 'JK Junior', '1234567890121', 'Romance', '69.69', 'professor.dumbledore.101', '100', 'http://www.book1.com', 'http://www.book1.com', 'true','false');

INSERT INTO User (abn,accounttype,address,createat,fullname,password,phonenumber,registerstatus,token,updateat,username)
VALUES ('12345678901','Admin','1234567890','2021-10-12 10:12:40.635','Samuel','$2a$10$SWVi4VKIyOLAo8JRm2CPBeLLMbd2YUb4xqGT3SzsNs8jSnWLm5C2K','1234567890','registered',
'token1','2021-10-12 10:12:43.324','1@email.com');

INSERT INTO User (abn,accounttype,address,createat,fullname,password,phonenumber,registerstatus,token,updateat,username)
VALUES ('12345678901','Customer','1234567890','2021-10-12 10:12:40.635','Samuel','$2a$10$SWVi4VKIyOLAo8JRm2CPBeLLMbd2YUb4xqGT3SzsNs8jSnWLm5C2K','1234567890','registered',
'token2','2021-10-12 10:12:43.324','2@email.com');


(setup backend microservices: do for each:)
<Intellij -> edit configurations -> add maven -> (see pic) name it, locate microservice folder, type in command>



(change 1002 to portnumber in backend application.properties file)