### curl samples (application deployed at application context `diploma_war_exploded`).

#### get All Users
`curl -s http://localhost:8080/diploma_war_exploded/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -s http://localhost:8080/diploma_war_exploded/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get admin profile with votes
curl -s http://localhost:8080/diploma_war_exploded/rest/admin/users/100001/with-votes --user admin@gmail.com:admin

#### get Profile
`curl -s http://localhost:8080/diploma_war_exploded/rest/profile --user user@yandex.ru:password`

#### get all restaurants
'curl -s http://localhost:8080/diploma_war_exploded/rest/restaurant/ --user admin@gmail.com:admin'

#### get restaurant
'curl -s http://localhost:8080/diploma_war_exploded/rest/restaurant/100002 --user admin@gmail.com:admin'

#### get restaurant with dishes
'curl -s http://localhost:8080/diploma_war_exploded/rest/restaurant/100002/with-dishes --user admin@gmail.com:admin'

#### get votes
'curl -s http://localhost:8080/diploma_war_exploded/rest/vote --user user@yandex.ru:password'

#### get vote
'curl -s http://localhost:8080/diploma_war_exploded/rest/vote/100007 --user user@yandex.ru:password'


