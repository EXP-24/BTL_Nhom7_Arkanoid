# ARKANOID!
Thể loại: Arcade / Brick Breaker

## Giới thiệu:
Arkanoid là một tựa game arcade cổ điển, nơi người chơi điều khiển thanh paddle để đập bóng phá gạch. Game được phát triển bằng JavaFX và FXML, với nhiều loại gạch khác nhau và power-up đa dạng. Mục tiêu của game là phá hết tất cả gạch trên màn hình để chiến thắng.

## Cấu trúc project:
Project được chia thành nhiều package và class để dễ cho việc quản lý:
- controllers - Quản lí các giao diện, Menu chính, Pause Menu và các sự kiện như Game over, hiện Score,...
- game - Quản lí cơ chế game, logic va chạm, paddle, bóng, gạch, power-up,...
- game.bricks - Quản lí các loại gạch (NormalBrick, Strong Brick, MapBrick...)
- game.powerups - Quản lí các power-up khác nhau trong game
- game.sounds - Quản lí nhạc nền và âm thanh

## UML
Dưới đây là sơ đồ UML của project để dễ hình dung mối quan hệ giữa các lớp:
<img width="2831" height="2504" alt="UML" src="https://github.com/user-attachments/assets/ee56cb34-4609-4f2d-a9f0-b9dd9a603cc2" />





## Các tính năng chính:
- Điều khiển paddle bằng A (Trái), D (Phải)
- Bắt đầu game / tiếp tục: SPACE
- Pause game: ESC
- Nhiều loại gạch với cơ chế khác nhau (NormalBrick, Strong Brick,...)

- Power-up đa dạng: tăng/giảm tốc độ bóng, thêm bóng phụ,...
- Có nhạc nền và các hiệu ứng âm thanh
- Quản lý điểm số, restart game, và nhiều level
- Pause menu, menu chính,... với giao diện JavaFX

## Nhạc nền và hiệu ứng:
- Hiệu ứng âm thanh khi bóng va chạm, phá gạch, nhận power-up
- Có các loại nhạc nền ứng với mỗi màn hình như:
  + 06. shining eyes (In-game BGM): Ở màn hình gameplay.
  + 11 Journey's End: Ở màn hình victory.
  ...
## Phân công nhiệm vụ
- Phạm Đức Cường (Nhóm trưởng): Quản lí paddle, bóng, số HP, va chạm, các chức năng của Menu chính,  thiết kế giao diện.
    + Paddle: Điều khiển Paddle di chuyển trái phải bằng nút A D.
    + Ball: Ban đầu bóng dính trên paddle và khi ấn SPACE thì bóng sẽ bay lên, bóng sẽ đổi hướng mỗi khi va chạm với paddle, brick hay là rìa màn hình chơi.
    + HP: Hiển thị số mạng lên màn hình và xử lí việc trừ mạng mỗi lần bóng rơi khỏi màn hình.
    + Va chạm: Kiểm tra va chạm của bóng với giới hạn khung, các object khác như paddle hay brick và việc tương tác với paddle và power up rơi xuống, khi power up chạm vào paddle thì power up sẽ biến mất và sẽ kích hoạt effect, sau thi hết hiệu lực thì sẽ trở về trạng thái bình thường.
    + Main Menu: Có các chức năng sau
        + Start: Khi ấn vào thì sẽ hiện ra màn hình gameplay.
        + Collection: Khi ấn vào thì sẽ hiện ra một màn hình để ta có thể lựa chọn skin cho paddle.
        + Credits: Khi ấn vào thì sẽ hiện ra màn hình danh sách các thành viên đã đóng góp vào việc phát triển game, ấn nút ENTER để quay lại Main Menu.
        + Exit: Khi ấn vào thì sẽ thoát ra khỏi trò chơi.
    + Design: Thiết kế giao diện cho phần Main Menu, Pause Menu, màn hình Gameplay 
- Đỗ Trọng An: Quản lí gạch, thiết kế map, boss level, chức năng chuyển level, load map, JUnit test.
    + Bricks: Tạo ra các loại brick khác nhau như normal brick, strong brick, unbreak brick, brick boss.
    + Map: Xây dựng nhiều map khác nhau dựa trên các loại brick.
    + Level: Game có 12 level, mỗi một level là một map, khi màn hình không còn brick nữa thì sẽ sang level tiếp theo (trừ unbreak brick).
    + JUnit Test: Tạo các test để kiểm tra như BrickTest, GameManagerTest, ScoreManagerTest, PowerUpTest.
- Nguyễn Tuấn Anh: Quản lí các power-up của paddle, chức năng của Score, phần Victory, thiết kế UML.
    + Paddle Power Up: Xây dựng các power up cho paddle như mở rộng paddle, thu hẹp paddle, cho paddle bắn ra đạn.
    + Score: Hiển thị, cập nhật các loại điểm lên màn hình chơi như điểm hiện tại, điểm cao nhất, bảng 10 điểm cao nhất, điểm sẽ cập nhật khi người chơi thua hoặc thắng game.
    + Victory: Hiển thị màn hình phần kết thú game khi người chơi hoàn thành tất cả các màn chơi, khi ấn nút ENTER sẽ chạy màn hình Credits
    + UML: Thiết kế UML dựa theo các class của game.
- Khương Tuấn Anh: Quản lí các power-up của bóng, Pause menu, phần âm thanh, phần Game Over.
    + Ball Power Up: Xây dựng các power up cho bóng như thu nhỏ, tăng tốc, làm chậm, nhân 3.
    + Pause Menu: Tạo ra một màn hình để có thể tạm dừng game
        + Resume: Khi ấn nút sẽ tiếp tục quá trình chơi.
        + Restart: Khi ấn nút sẽ reset lại trò chơi.
        + Quit: Khi ấn nút sẽ quay lại Menu chính.
    + Game Over: Người chơi sẽ thua khi số mạng trở về 0 và sẽ hiện ra màn hình game over, người chơi có thể lựa chọn chơi lại hoặc quay về menu chính
    + Music & Sound: Thêm các loại nhạc nền cho game như nhạc Main Menu, Credits, Gameplay, Game Over, Victory cũng như các loại sound effect cho việc bóng va chạm, ăn power up

