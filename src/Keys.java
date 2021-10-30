public class Keys {
    public boolean forward;
    public boolean back;
    public boolean left;
    public boolean right;

    public Keys() {
        forward = false;
        back = false;
        left = false;
        right = false;
    }

    public void add(char c) {
        switch(c) {
            case 'w':
                forward = true;
                break;
            case 's':
                back = true;
                break;
            case 'a':
                left = true;
                break;
            case 'd':
                right = true;
                break;
        }
    }

    public void remove(char c) {
        switch(c) {
            case 'w':
                forward = false;
                break;
            case 's':
                back = false;
                break;
            case 'a':
                left = false;
                break;
            case 'd':
                right = false;
                break;
        }
    }
}
