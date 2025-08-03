create table ingressos(
    id bigserial not null,
    evento_id bigserial not null,
    total_ingressos integer not null default 0,
    ingressos_disponiveis integer not null default 0,
    preco decimal(10,2) not null default 0.00,

    primary key(id),
    foreign key(evento_id) references eventos(id) on delete cascade
);
