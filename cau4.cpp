#include <iostream>
using namespace std;

struct Rectangle {
    int height;
    int length;

    // Hàm khởi tạo mặc định
    Rectangle() : height(0), length(0) {}

    // Hàm khởi tạo nhận vào độ dài 2 cạnh
    Rectangle(int h, int l) : height(h), length(l) {}

    // Hàm tính và trả về chu vi hình chữ nhật
    int getPerimeter() {
        return 2 * (height + length);
    }

    // Hàm vẽ ra hình chữ nhật rỗng
    void print() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < length; ++j) {
                if (i == 0 || i == height - 1 || j == 0 || j == length - 1) {
                    cout << "*";
                } else {
                    cout << " ";
                }
            }
            cout << endl;
        }
    }
};

// Hàm so sánh chu vi hai hình chữ nhật
int compare(Rectangle a, Rectangle b) {
    int perimeterA = a.getPerimeter();
    int perimeterB = b.getPerimeter();
    if (perimeterA < perimeterB) return -1;
    else if (perimeterA == perimeterB) return 0;
    else return 1;
}
