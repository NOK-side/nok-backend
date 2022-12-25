INSERT INTO `nok-project`.tourist_spot (id, name, type, road_name_address, land_number_address, latitude, longitude, area,
                          public_benefit_facilities, accommodation, amusement, recreational_facilities,
                          customer_service_facilities, supporting_facilities, register_date, number_of_capacity,
                          number_of_parking, description, management_agency_phone_number, management_agency_name,
                          data_base_date)
VALUES (1, '고래불관광지', '관광지', '경상북도 영덕군 병곡면 병곡리 72-8', '경상북도 영덕군 병곡면 병곡리 72-8', 36.6003009541, 129.4105747991, 880400,
        '관리사무소(2동)+주차장+화장실(6동)+샤워장(5동)+하계휴양소+취사동(4동)', null, '비치발리볼경기장+자전거대여점+풋살구장+체력단련시설', '야영장+연수원', '상가(2동)', null,
        '1988-03-28', 17100, 1000, '명사20리 라고 불리는 8km에 이르는 넓고 긴 양질의 백사장과 빼어난 해송림 등 우수한 자연자원을 보유한 관광지', '054-730-6513',
        '경상북도 영덕군청', '2022-08-24');

INSERT INTO `nok-project`.tourist_spot (id, name, type, road_name_address, land_number_address, latitude, longitude, area,
                          public_benefit_facilities, accommodation, amusement, recreational_facilities,
                          customer_service_facilities, supporting_facilities, register_date, number_of_capacity,
                          number_of_parking, description, management_agency_phone_number, management_agency_name,
                          data_base_date)
VALUES (2, '장사해수욕장관광지', '관광지', '경상북도 영덕군 남정면 동해대로 3592', '경상북도 영덕군 남정면 장사리 74-1', 36.2824187049, 129.3755938472, 104219,
        '관리사무소+주차장+화장실(3동)+샤워장(2동)+광장', '방갈로(20동)', '미니축구장', '전승기념마당', '상가(2동)', null, '1981-12-31', 2500, 297,
        '해수욕장의 청정 자연자원과 장사상륙작전이라는 역사적 사건을 보존한 관광지', '054-730-6513', '경상북도 영덕군청', '2022-08-24');

INSERT INTO `nok-project`.members (id, email, member_id, name, phone_number, profile_image, refresh_token, user_agent, password, role, status) VALUES (1, 'rkdals2134@naver.com', 'rkdals2134', 'tester', '01000000000', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/b8f0d860-f08b-438d-b?alt=media', 'eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJrZGFsczIxMzRAbmF2ZXIuY29tIiwiaWF0IjoxNjY4NjAyMzUwLCJleHAiOjE2Njg2MDI1MzB9.NRjklgIJwaJhP7zUdtZqaYh3z3DrNIXeqT1sRTffZk0', 'PostmanRuntime/7.29.2', '72ab994fa2eb426c051ef59cad617750bfe06d7cf6311285ff79c19c32afd236', 'STORE', 'ACTIVE');

INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (1, 0, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 0 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 0', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (2, 1, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 1 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 1', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (3, 2, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 2 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 2', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (4, 3, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 3 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 3', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (5, 4, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 4 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 4', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (6, 5, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 5 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 5', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (7, 6, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 6 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 6', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (8, 7, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 7 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 7', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (9, 8, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 8 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 8', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (10, 9, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 9 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 9', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (11, 10, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 10 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 10', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (12, 11, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 11 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 11', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (13, 12, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 12 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 12', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (14, 13, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 13 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 13', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (15, 14, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 14 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 14', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (16, 15, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 15 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 15', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (17, 16, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 16 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 16', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (18, 17, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 17 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 17', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (19, 18, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 18 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 18', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (20, 19, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 19 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 19', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (21, 20, 'ACTIVE', 9, 18, '123123123', 'RESTAURANT', '이것은 20 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕', 'SUNDAY', '', '지번 주소', 38.2120000000, 128.5970000000, '도로명 주소', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '테스트 상점 20', '000-0000-0000');
INSERT INTO `nok-project`.store (id, owner_id, status, from_hour, to_hour, business_number, category, description, holidays, keyword, land_number_address, latitude, longitude, road_name_address, menu_picture_url, name, phone_number) VALUES (22, 1, 'ACTIVE', 9, 18, '123123123', 'CAFE', '밑반찬부터 생선조림까지 몽땅 맛있는 콩새식당', 'SUNDAY', '속초|가자미|가자미찜|생선조림', '강원 속초시 동명동 53-17', 38.2120613000, 128.5985800000, '강원 속초시 영금정로2길 9-1', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/0649168f-fbb6-4aad-9?alt=media', '콩새식당', '000-0000-0000');

INSERT INTO `nok-project`.store_image (id, image_url, status, store_id) VALUES (1, 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/f3d2b37e-7e95-4c80-9?alt=media', 'ACTIVE', 22);
INSERT INTO `nok-project`.store_image (id, image_url, status, store_id) VALUES (2, 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/c36b8e9d-7f2d-499b-8?alt=media', 'ACTIVE', 22);
INSERT INTO `nok-project`.store_image (id, image_url, status, store_id) VALUES (3, 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/2234c81a-b370-43e8-9?alt=media', 'ACTIVE', 22);

INSERT INTO `nok-project`.gifticon (id, category, image_url, notice, order_cancellation_period, period, price, product_name, refund_and_exchange_instruction, status, store_id) VALUES (1, 'CAFE', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '공지사항', 30, 90, 10000.00, 'gifticon 1', '이건 환불 절대 안됨 교환도 안됨', 'ACTIVE', 1);
INSERT INTO `nok-project`.gifticon (id, category, image_url, notice, order_cancellation_period, period, price, product_name, refund_and_exchange_instruction, status, store_id) VALUES (2, 'CAFE', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '공지사항', 60, 30, 15000.00, 'gifticon 2', '이건 환불 절대 안됨 교환도 안됨', 'ACTIVE', 1);



INSERT INTO `nok-project`.mission_group (id, description, image_url, land_number_address, latitude, longitude, road_name_address, prize_id, sub_title, title, tourist_spot_id) VALUES (1, '고래불 관광지에서 진행하는 미션들이랍니다', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '경상북도 영덕군 병곡면 병곡리 72-8', 36.6003009541, 129.4105747991, '경상북도 영덕군 병곡면 병곡리 72-8', 1, '고래고래고래밥', '고래불 미션', 1);
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (1, '첫번째 미션은 문제를 푸는겁니다', '1cOQSVEK3UR8T2RfBHaQGd-WpgJbhnJJfEFIZQ0uDDik', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '경상북도 영덕군 병곡면 병곡리 72-8', 36.6003009541, 129.4105747991, '경상북도 영덕군 병곡면 병곡리 72-8', 20, '첫번째일까?', '첫번째 미션', 'QR_WITH_QUESTION', 1, 'https://docs.google.com/forms/d/e/1FAIpQLSfy_X89_GqnDsa5UxO-poCU5ixm-7hAKHW7qdEAo2_k0KxMWw/viewform?usp=sf_link');
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (2, '두번째 미션은 특정 위치에 도착하는것입니다', '1', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '경상북도 영덕군 병곡면 병곡리 72-8', 36.6004956000, 129.4106550000, '경상북도 영덕군 병곡면 병곡리 72-8', 50, '두번째일까?', '두번째 미션', 'CURRENT_USER_LOCATION', 1, '');

INSERT INTO `nok-project`.mission_group (id, description, image_url, land_number_address, latitude, longitude, road_name_address, prize_id, sub_title, title, tourist_spot_id) VALUES (2, '장사해수욕장에서 진행하는 미션들이랍니다', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '경상북도 영덕군 남정면 장사리 74-1', 36.2824187049, 129.3755938472, '경상북도 영덕군 남정면 동해대로 3592', 1, '고래고래고래밥', '고래불 미션', 2);
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (3, '첫번째 미션은 특정 위치에 도착하는것입니다', '2', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '경상북도 영덕군 남정면 장사리 74-1', 36.2824187049, 129.3755938472, '경상북도 영덕군 남정면 동해대로 3592', 50, '첫번째일까?', '첫번째 미션', 'CURRENT_USER_LOCATION', 2, '');
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (4, '두번째 미션은 특정 위치에 도착하는것입니다', '3', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '경상북도 영덕군 남정면 장사리 74-1', 36.2824187049, 129.3755938472, '경상북도 영덕군 남정면 동해대로 3592', 50, '두번째일까?', '두번째 미션', 'CURRENT_USER_LOCATION', 2, '');

INSERT INTO `nok-project`.mission_group (id, description, image_url, land_number_address, latitude, longitude, road_name_address, prize_id, sub_title, title, tourist_spot_id) VALUES (3, '쏠비치 삼척에서 진행하는 미션들이랍니다', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '강원도 삼척시 갈천동 225', 37.4703535, 129.164666, '강원 삼척시 수로부인길 453', 1, '고래고래고래밥', '고래불 미션', 2);
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (5, '첫번째 미션은 특정 위치에 도착하는것입니다', '4', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '강원도 삼척시 갈천동 225', 37.4703535, 129.164666, '강원 삼척시 수로부인길 453', 50, '첫번째일까?', '첫번째 미션', 'CURRENT_USER_LOCATION', 3, '');
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (6, '두번째 미션은 특정 위치에 도착하는것입니다', '5', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '강원도 삼척시 갈천동 225', 37.4703535, 129.164666, '강원 삼척시 수로부인길 453', 50, '두번째일까?', '두번째 미션', 'CURRENT_USER_LOCATION', 3, '');

INSERT INTO `nok-project`.mission_group (id, description, image_url, land_number_address, latitude, longitude, road_name_address, prize_id, sub_title, title, tourist_spot_id) VALUES (4, '장사해수욕장에서 진행하는 미션들이랍니다', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '부산광역시 수영구 광안동 192-20', 35.1538826, 129.118628, '부산 수영구 광안해변로 219', 1, '고래고래고래밥', '고래불 미션', 2);
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (7, '첫번째 미션은 특정 위치에 도착하는것입니다', '6', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '부산광역시 수영구 광안동 192-20', 35.1538826, 129.118628, '부산 수영구 광안해변로 219', 50, '첫번째일까?', '첫번째 미션', 'CURRENT_USER_LOCATION', 4, '');
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (8, '두번째 미션은 특정 위치에 도착하는것입니다', '7', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '부산광역시 수영구 광안동 192-20', 35.1538826, 129.118628, '부산 수영구 광안해변로 219', 50, '두번째일까?', '두번째 미션', 'CURRENT_USER_LOCATION', 4, '');

INSERT INTO `nok-project`.mission_group (id, description, image_url, land_number_address, latitude, longitude, road_name_address, prize_id, sub_title, title, tourist_spot_id) VALUES (5, '장사해수욕장에서 진행하는 미션들이랍니다', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '경부산 해운대구 송정동  712-2', 35.1794606, 129.199337, '부산 해운대구 송정동  712-2', 1, '고래고래고래밥', '고래불 미션', 2);
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (9, '첫번째 미션은 특정 위치에 도착하는것입니다', '8', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '부산 해운대구 송정동  712-2', 35.1794606, 129.199337, '부산 해운대구 송정동  712-2', 50, '첫번째일까?', '첫번째 미션', 'CURRENT_USER_LOCATION', 5, '');
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (10, '두번째 미션은 특정 위치에 도착하는것입니다', '9', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '부산 해운대구 송정동  712-2', 35.1794606, 129.199337, '부산 해운대구 송정동  712-2', 50, '두번째일까?', '두번째 미션', 'CURRENT_USER_LOCATION', 5, '');

INSERT INTO `nok-project`.mission_group (id, description, image_url, land_number_address, latitude, longitude, road_name_address, prize_id, sub_title, title, tourist_spot_id) VALUES (6, '장사해수욕장에서 진행하는 미션들이랍니다', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '서울특별시 종로구 사직로 161 (세종로, 경복궁)', 37.5806736, 126.976952, '서울 종로구 사직로 161 경복궁', 1, '고래고래고래밥', '고래불 미션', 2);
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (11, '첫번째 미션은 특정 위치에 도착하는것입니다', '10', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '서울특별시 종로구 사직로 161 (세종로, 경복궁)', 37.5806736, 126.976952, '서울 종로구 사직로 161 경복궁', 50, '첫번째일까?', '첫번째 미션', 'CURRENT_USER_LOCATION', 6, '');
INSERT INTO `nok-project`.mission (id, description, form_id, image_url, land_number_address, latitude, longitude, road_name_address, qualification, sub_title, title, type, mission_group_id, question_url) VALUES (12, '두번째 미션은 특정 위치에 도착하는것입니다', '11', 'https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media', '서울특별시 종로구 사직로 161 (세종로, 경복궁)', 37.5806736, 126.976952, '서울 종로구 사직로 161 경복궁', 50, '두번째일까?', '두번째 미션', 'CURRENT_USER_LOCATION', 6, '');
