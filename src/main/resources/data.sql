insert into members (id, account, name, status) values (1, 'ed', 'Edward', 'ACTIVE');
insert into members (id, account, name, status) values (2, 'amy', 'Amy', 'ACTIVE');
insert into members (id, account, name, status) values (3, 'blocked', '停權會員', 'SUSPENDED');

insert into event_sessions (id, title, ticket_price, remaining_capacity, status, start_at)
values (101, 'Java Conference', 2100, 50, 'OPEN', '2026-11-10 09:00:00');
insert into event_sessions (id, title, ticket_price, remaining_capacity, status, start_at)
values (102, 'Spring Workshop', 800, 0, 'OPEN', '2026-11-05 13:00:00');
insert into event_sessions (id, title, ticket_price, remaining_capacity, status, start_at)
values (103, 'Spring Data Lab', 600, 10, 'OPEN', '2026-11-08 10:00:00');
insert into event_sessions (id, title, ticket_price, remaining_capacity, status, start_at)
values (104, 'MySQL Clinic', 500, 8, 'OPEN', '2026-11-15 14:00:00');
insert into event_sessions (id, title, ticket_price, remaining_capacity, status, start_at)
values (105, 'Legacy Meetup', 300, 20, 'CLOSED', '2026-11-20 19:00:00');

insert into bookings
    (id, booking_no, event_id, member_id, quantity, unit_price, total_amount, status, booked_at)
values
    (1, 'T-DEMO-001', 101, 2, 1, 1200, 1200, 'PAID', '2026-10-25 10:00:00');
insert into bookings
    (id, booking_no, event_id, member_id, quantity, unit_price, total_amount, status, booked_at)
values
    (2, 'T-DEMO-002', 104, 1, 1, 500, 500, 'CANCELLED', '2026-10-26 11:00:00');
insert into bookings
    (id, booking_no, event_id, member_id, quantity, unit_price, total_amount, status, booked_at)
values
    (3, 'T-DEMO-003', 103, 1, 1, 600, 600, 'PENDING', '2026-10-27 12:00:00');

