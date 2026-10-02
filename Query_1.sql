create table car (
                     id int auto_increment primary key,
                     brand varchar(50) not null,
                     model varchar(50) not null,
                     color varchar(20),
                     year int not null,
                     total_km double default 0.0,
                     current_speed_in_kph double default 0.0,
                     registered_at timestamp default current_timestamp,
                     last_speed_change_at timestamp null default null,

                     transmission enum('Manual', 'Automatic', 'Semi-Automatic') not null,
                     is_engine_on boolean default false,
                     current_fuel_in_l double default 0.0,
                     tank_capacity_in_l double default 0.0,
                     fuel_consumption_in_kpl double default 0.0,

                     total_door int default 4,
                     is_doors_locked boolean default false,
                     total_seat int default 4,

                     owner_id int,
                     foreign key (owner_id) references passenger(id)
);

create table motorcycle (
                            id int auto_increment primary key,
                            brand varchar(50) not null,
                            model varchar(50) not null,
                            color varchar(20),
                            year int not null,
                            total_km double default 0.0,
                            current_speed_in_kph double default 0.0,
                            registered_at timestamp default current_timestamp,
                            last_speed_change_at timestamp null default null,

                            transmission enum('Manual', 'Automatic', 'Semi-Automatic') not null,
                            is_engine_on boolean default false,
                            current_fuel_in_l double default 0.0,
                            tank_capacity_in_l double default 0.0,
                            fuel_consumption_in_kpl double default 0.0,

                            is_kickstand_down boolean default true,

                            owner_id int,
                            foreign key (owner_id) references passenger(id)
);

create table bicycle (
                         id int auto_increment primary key,
                         brand varchar(50) not null,
                         model varchar(50) not null,
                         color varchar(20),
                         year int not null,
                         total_km double default 0.0,
                         current_speed_in_kph double default 0.0,
                         registered_at timestamp default current_timestamp,
                         last_speed_change_at timestamp null default null,
                         owner_id int,

                         total_gear int default 1,
                         current_gear int default 1,
                         foreign key (owner_id) references passenger(id)
);

create table passenger (
                           id int auto_increment primary key,
                           name varchar(100) not null,
                           age int not null,
                           gender enum('Male', 'Female', 'Other') not null,
                           vehicle_model enum('Car', 'Motorcycle', 'Bicycle'),
                           vehicle_id int
);
