drop database if exists ssjg;
create database ssjg;
use ssjg;



drop table if exists member;
create table member(
	memberNo bigint unsigned auto_increment ,
    memberId varchar(30) not null unique ,
    memberPw varchar(30) not null ,
	memberName varchar(20) not null ,
    memberEmail varchar(255) not null unique ,
    memberPhone varchar(13) not null unique ,
    memberGrant int ,
    memberdate datetime default now() ,
    primary key(memberNo)
);

insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'안산운송' ,'1'	,'geonwk95@naver.com' ,'김건우'	,'010-0000-0000' ,11
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'부산운송' ,'1'	,'lsh87@naver.com' ,'이승호'	,'010-0000-0001' ,12
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'대구운송' ,'1'	,'zxc123@naver.com' ,'오승택'	,'010-0000-0002' ,13
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'광주운송' ,'1'	,'tlfjb123@naver.com' ,'차준영'	,'010-0000-0003' ,14
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'인천운송' ,'1'	,'jglg@naver.com' ,'유재석'	,'010-0000-0004' ,15
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'동서울운송' ,'1'	,'vvv123@naver.com' ,'신동엽'	,'010-0000-0005' ,16
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'건우운송' ,'1'	,'ggg123@naver.com' ,'하하'	,'010-0000-0006' ,17
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'qwe123' ,'qwe123'	,'qwe@qwe.qwe' ,'김만덕'	,'010-0000-0010' ,1
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'qwe1234' ,'qwe1234'	,'qwer@qwe.qwe' ,'이상덕'	,'010-0000-0011' ,1
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'asd123' ,'asd123'	,'asd@qwe.qwe' ,'용가리'	,'010-0000-0012' ,1
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'asd1234' ,'asd1234'	,'asdf@qwe.qwe' ,'파이리'	,'010-0000-0013' ,1
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'zxc123' ,'zxc1234'	,'zxc@qwe.qwe' ,'꼬부기'	,'010-0000-0014' ,1
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'ewq321' ,'ewq321'	,'zxcv@qwe.qwe' ,'짱구'	,'010-0000-0015' ,1
);
insert into member(memberId , memberPw , memberEmail , memberName , memberPhone , memberGrant) values(
	'dsa321' ,'dsa321'	,'ewq@qwe.qwe' ,'스폰지밥'	,'010-0000-0016' ,1
);


select * from member;

drop table if exists busClass;
create table busClass (
	busClassNo int unsigned auto_increment ,
    busClassName varchar(30) not null unique,
    busClassSeat int not null ,
    busClassPrice int ,
    busClassDate datetime default now() ,
    primary key(busClassNo)
);

insert into busClass(busClassName,busClassSeat,busClassPrice) values(
	'일반',
    '40',
	'5000'
);
insert into busClass(busClassName,busClassSeat,busClassPrice) values(
	'우등',
    '30',
	'7000'
);

insert into busClass(busClassName,busClassSeat,busClassPrice) values(
	'프리미엄',
    '20',
	'10000'
);
select * from busClass;

drop table if exists bus;
create table bus(
	busNo int unsigned auto_increment ,
    busNumber varchar(30) not null ,
    busClassNo int unsigned,
    memberNo bigint unsigned ,
    busDate datetime default now() ,
    busPosition varchar(50) not null,
	primary key(busNo) ,
    foreign key( busClassNo ) references busClass( busClassNo ) on delete set null on update cascade ,
    foreign key( memberNo ) references member( memberNo ) on delete set null on update cascade
);

insert into bus(busNumber,busClassNo,memberNo,busPosition) values('23너2345','1','8',"동서울");
insert into bus(busNumber,busClassNo,memberNo,busPosition) values('33너3365','1','8',"부산");
insert into bus(busNumber,busClassNo,memberNo,busPosition) values('43너1234','3','9',"광주");
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('47가1234','1','9','안산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('20거3334','1','10','안산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('34다4534','2','10','안산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('22나8734','2','11','안산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('52나1234','1','11','안산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('34거1134','2','12','안산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('23가1234','3','12','안산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('23마6234','1','13','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('43아8234','3','13','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('64바1454','2','14','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('98버1294','1','14','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('41바1004','1','8','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('34사1874','2','8','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('19사1204','3','9','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('94자3444','2','9','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('11아3458','1','10','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('23가0542','2','10','부산');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('24아3495','1','11','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('34사0346','3','11','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('88아1348','1','12','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('54거3455','1','12','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('12너3243','2','13','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('88너5768','3','13','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('08버4788','3','14','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('32가2005','1','14','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('68사4002','3','1','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('31아4923','2','1','대구');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('45바3919','2','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('42아2339','1','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('12사1229','3','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('23가9999','2','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('64너1101','3','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('42너3910','1','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('96거3940','2','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('00나1023','1','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('14버1921','3','1','인천');
insert into bus(busNumber,busClassNo,memberNo, busPosition) values('01바1019','1','1','인천');


select * from bus;

drop table if exists route;
create table route(
	routeNo int unsigned auto_increment ,
    routePrice int ,
	routeStartTerminal varchar(50) not null ,
    routeEndTerminal varchar(50) not null ,
    routeDate datetime default now() ,
    primary key(routeNo)
);


insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','안산','부산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','안산','대구');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','안산','인천');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','안산','광주');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','부산','안산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','부산','대구');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','부산','인천');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','부산','광주');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','부산','동서울');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','대구','부산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','대구','안산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','대구','인천');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','대구','광주');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','인천','부산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','인천','대구');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','인천','안산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','인천','광주');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('15000','건우','짱짱');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','동서울','부산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','동서울','부산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','동서울','대구');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','동서울','인천');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','동서울','광주');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','광주','동서울');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','광주','부산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','광주','대구');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','광주','부산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','광주','안산');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','안산','동서울');
insert into route(routePrice,routeStartTerminal,routeEndTerminal) values('20000','안산','광주');


select * from route;

drop table if exists routeTime;
create table routeTime(
	routeTimeNo int unsigned auto_increment ,
    routeTimeStartTime datetime not null ,
    routeTimeEndTime datetime  not null ,
    routeTimeStatus int not null ,
    busNo int unsigned ,
    routeNo int unsigned ,
    routeTimeDate datetime default now() ,
    primary key(routeTimeNo) ,
    foreign key(busNo) references bus(busNo) on delete set null on update cascade ,
    foreign key(routeNo) references route(routeNo) on delete set null on update cascade
);

insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-07 14:00:00','2024-05-01 12:00:00','0','40','2');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-08 15:00:00','2024-05-01 13:00:00','1','39','2');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-07 15:00:00','2024-05-01 14:00:00','0','39','4');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-09 16:00:00','2024-05-01 15:00:00','1','38','4');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-08 16:00:00','2024-05-01 16:00:00','0','37','6');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-07 18:00:00','2024-05-01 17:00:00','1','37','6');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-08 17:00:00','2024-05-01 18:00:00','0','36','8');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-09 18:00:00','2024-05-01 19:00:00','1','36','8');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-07 17:00:00','2024-05-01 20:00:00','0','35','10');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-08 19:00:00','2024-05-01 21:00:00','1','35','10');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-07 20:00:00','2024-05-01 22:00:00','0','3','12');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-08 21:00:00','2024-05-01 23:00:00','1','3','12');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-09 22:00:00','2024-05-02 01:00:00','0','4','14');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-03-09 23:00:00','2024-05-03 02:00:00','1','4','14');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 12:00:00','2024-03-08 14:00:00','1','5','16');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-01 13:00:00','2024-03-07 15:00:00','0','5','3');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 13:00:00','2024-03-07 15:00:00','1','6','3');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-01 14:00:00','2024-03-07 16:00:00','0','11','5');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 14:00:00','2024-03-07 16:00:00','1','11','5');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-01 15:00:00','2024-03-07 17:00:00','0','12','7');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 15:00:00','2024-03-07 17:00:00','1','12','7');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-01 16:00:00','2024-03-07 18:00:00','0','21','9');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 16:00:00','2024-03-07 18:00:00','1','21','9');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-01 17:00:00','2024-03-07 19:00:00','0','22','11');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 17:00:00','2024-03-07 19:00:00','1','22','11');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-01 18:00:00','2024-03-07 20:00:00','0','34','13');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','34','13');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','33','1');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','33','1');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','32','15');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','32','15');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','17');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','17');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','18');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','18');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','19');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','19');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','20');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','20');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','21');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','21');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','22');
insert into routeTime(routeTimeStartTime,routeTimeEndTime,routeTimeStatus,busNo,routeNo) values('2024-05-02 18:00:00','2024-03-07 20:00:00','1','31','22');



select * from routeTime;

drop table if exists reservation;
create table reservation(
	reservationNo int unsigned auto_increment ,
    reservationSeatNum int not null , -- 예약한 시트
    reservationStatus int not null , -- 버스 출발 표시
	reservationPrice int not null, -- 예약 총 가격
    memberNo bigint unsigned ,
    routeTimeNo int unsigned ,
    reservationDate datetime default now() ,
    primary key( reservationNo ) ,
    foreign key( memberNo ) references member( memberNo ) on delete set null on update cascade,
    foreign key( routeTimeNo ) references routeTime( routeTimeNo ) on delete set null on update cascade
);
select * from reservation r inner join member m on r.memberNo = m.memberNo inner join routeTime rt on r.routeTimeNo = rt.routeTimeNo inner join route ro on rt.routeNo = ro.routeNo;

select * from reservation r inner join member m on r.memberNo = m.memberNo inner join routeTime rt on r.routeTimeNo = rt.routeTimeNo inner join route ro on rt.routeNo = ro.routeNo;
# select * from reservation r inner join member m on r.memberNo = m.memberNo inner join route ro on r.routeNo = ro.routeNo where reservationDate like '%2024%';
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	11,
    0,
	13000,
	1,
	1
);
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	12,
    1,
	14500,
	1,
	9
);
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	13,
    0,
	17500,
	2,
	4
);
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	14,
    1,
	16600,
	2,
	7
);
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	15,
    1,
	18900,
	3,
	5
);
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	15,
    0,
	20000,
	11,
	6
);
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	16,
    1,
	12000,
	11,
	2
);
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	1,
    0,
	15000,
	12,
	3
);
insert into reservation(reservationSeatNum,reservationStatus,reservationprice,memberNo,routeTimeNo) values(
	3,
    1,
	13400,
	12,
	4
);
update reservation set reservationStatus = 0 where memberNo = 1;
update reservation set reservationStatus = 1 where memberNo = 2;

-- select * from reservation r inner join member m on r.memberNo = m.memberNo inner join route ro on r.routeNo = ro.routeNo where reservationDate like '%2024%';
-- select * from reservation r inner join member m on r.memberNo = m.memberNo inner join route ro on r.routeNo = ro.routeNo where r.memberNo = 2;
-- select * from reservation r inner join member m on r.memberNo = m.memberNo where r.memberNo = 1;
-- select * from reservation r inner join member m on r.memberNo = m.memberNo where r.memberNo = 2;
-- select * from reservation where memberNo = 2;
select * from reservation;

-- select * from routeTime rt inner join route r on rt.routeNo = r.routeNo
-- where (routeStartTerminal like '%안산%' and routeTimeStatus = 0) or (routeStartTerminal like '%안산"%' and routeTimeStatus = 1);
-- select * from reservation r inner join member m on r.memberNo = m.memberNo inner join route ro on r.routeNo = ro.routeNo where r.memberNo = 2;

-- select * from routeTime rt inner join bus b on rt.busNo = b.busNo inner join route r on rt.routeNo = r.routeNo inner join busClass bc on b.busClassNo = bc.busClassNo
--  where (routeStartTerminal like '%안산%' and routeTimeStatus = 0) or (routeStartTerminal like '%안산%' and routeTimeStatus = 1);
--
--  select * from routeTime rt inner join bus b on rt.busNo = b.busNo inner join route r on rt.routeNo = r.routeNo inner join busClass bc on b.busClassNo = bc.busClassNo;
--  # where (routeEndTerminal like '%안산%' and routeTimeStatus = 0) or (routeEndTerminal like '%안산%' and routeTimeStatus = 1);
select * from reservation r inner join member m on r.memberNo = m.memberNo inner join routeTime rt on r.routeTimeNo = rt.routeTimeNo inner join route ro on rt.routeNo = ro.routeNo where memberGrant = 11 and routeStartTerminal like '%안산%' order by reservationNo desc limit 0,3;
-- select distinct routeStartTerminal from route where routeEndTerminal = '짱짱';

# ============ 추가기능 ) 승객 문의 게시판
drop table if exists inquiry;
create table inquiry(
	ino int unsigned auto_increment ,
    ipw varchar(30) not null ,
    ititle varchar(255) not null ,
    icontent longtext not null  ,
    istatus smallint default 0 ,
    idate datetime default now() ,
    memberNo bigint unsigned ,
    primary key( ino ) ,
    foreign key( memberNo ) references member( memberNo ) on update cascade on delete cascade
);
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목1' , '내용1' , 8 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목2' , '내용2' , 8 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목3' , '내용3' , 9 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목4' , '내용4' , 9 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목5' , '내용5' , 10 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목6' , '내용6' , 10 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목7' , '내용7' , 11 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목8' , '내용8' , 11 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목9' , '내용9' , 12 );
insert into inquiry( ipw , ititle , icontent , memberNo ) values ( 123 , '제목10' , '내용10' , 12 );


select * from inquiry;
select * from inquiry order by ino desc;
select * from inquiry order by idate desc limit 0 , 5;
select count(*) from inquiry;
select count(*) from inquiry where icontent like '%내용%';
select * from inquiry i inner join member m on i.memberNo = m.memberNo where i.ino=1;



# 2. 게시물
drop table if exists board;
create table board(
	bno bigint unsigned auto_increment ,
    btitle varchar( 255 ) not null ,
    bcontent longtext ,
    bfile varchar( 255 ) ,
    bview int unsigned default 0 not null ,
    bdate datetime  default now() not null  ,
    memberNo bigint unsigned ,
    constraint board_bno_pk primary key( bno ),
    constraint board_membermNo_fk foreign key( memberNo) references member( memberNo ) on update cascade on delete cascade
);
select *from board;

select * from board b inner join member m on b.memberNo= m.memberNo;
insert into board (btitle , bcontent , memberNo ) values('제목2','내용2','2');
update reservation set reservationStatus = 1 where memberNo = 11 and reservationNo = 10;