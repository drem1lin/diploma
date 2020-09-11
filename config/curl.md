### curl samples (application deployed at application context `diploma_war_exploded`).

#### get All Users
`curl -s http://localhost:8080/diploma_war_exploded/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -s http://localhost:8080/diploma_war_exploded/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get admin profile with votes
curl -s http://localhost:8080/diploma_war_exploded/rest/admin/users/100001/with-votes --user admin@gmail.com:admin

!!!#### register Users
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/diploma_war_exploded/rest/profile/register`
curl -H 'Content-Type: application/json;charset=UTF-8' -s -XPOST http://localhost:8080/diploma_war_exploded/rest/profile/register -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}'

#### get Profile
`curl -s http://localhost:8080/diploma_war_exploded/rest/profile --user user@yandex.ru:password`

#### get all restaurants
'curl -s http://localhost:8080/diploma_war_exploded/rest/restaurant --user admin@gmail.com:admin'

#### get restaurant
'curl -s http://localhost:8080/diploma_war_exploded/rest/restaurant/100002 --user admin@gmail.com:admin'

#### get restaurant with dishes
'curl -s http://localhost:8080/diploma_war_exploded/rest/restaurant/100002/with-dishes --user admin@gmail.com:admin'

#### get admin votes
'curl -s http://localhost:8080/diploma_war_exploded/rest/vote --user user@yandex.ru:password'

#### get vote
'curl -s http://localhost:8080/diploma_war_exploded/rest/vote/100007 --user user@yandex.ru:password'


