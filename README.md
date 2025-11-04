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
<img width="2671" height="2804" alt="UML_table" src="https://github.com/user-attachments/assets/358496d9-0dff-47fc-9d98-8486176fba18" />

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

## Phân công nhiệm vụ
- Phạm Đức Cường (Nhóm trưởng): Quản lí paddle, bóng, va chạm, các chức năng của Menu chính,  thiết kế giao diện.
- Đỗ Trọng An: Quản lí gạch, thiết kế level, boss level, chức năng chuyển map, load map.
- Nguyễn Tuấn Anh: Quản lí các power-up của paddle, chức năng của Score, phần Victory, thiết kế UML.
- Khương Tuấn Anh: Quản lí các power-up của bóng, Pause menu, phần âm thanh, phần Game Over.

