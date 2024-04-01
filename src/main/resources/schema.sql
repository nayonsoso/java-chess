create table chess_game (
    id int primary key auto_increment,
    status varchar(50) not null
);

create table movement (
    id int primary key auto_increment,
    source_file varchar(50),
    source_rank varchar(50),
    target_file varchar(50),
    target_rank varchar(50),
    chess_game_id int,
    foreign key (chess_game_id) references chess_game(id)
);
