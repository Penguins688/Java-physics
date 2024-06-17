class Particle {
    public final double gc = 0.1;
    public final double bc = -0.7;
    private double x;
    private double y;
    private double Xvel;
    private double Yvel;
    private double radius;
    private double mass;

    public Particle(double x, double y, double Xvel, double Yvel, double radius) {
        this.x = x;
        this.y = y;
        this.Xvel = Xvel;
        this.Yvel = Yvel;
        this.radius = radius;
        this.mass = 1.0;
    }

    public void update() {
        this.Yvel += gc;
        this.y += this.Yvel;
        this.x += this.Xvel;

        if (this.y + this.radius > 825) {
            this.y = 825 - this.radius;
            this.Yvel *= bc;
        } else if (this.y - this.radius < 0) {
            this.y = this.radius;
            this.Yvel *= bc;
        }

        if (this.x + this.radius > 825) {
            this.x = 825 - this.radius;
            this.Xvel *= bc;
        } else if (this.x - this.radius < 0) {
            this.x = this.radius;
            this.Xvel *= bc;
        }

        this.Xvel *= 0.99;
        this.Yvel *= 0.99;

        if (Math.abs(this.Xvel) < 0.01) this.Xvel = 0;
        if (Math.abs(this.Yvel) < 0.01) this.Yvel = 0;
    }

    public static boolean collide(double x, double y, double r, double a, double b, double c) {
        double squaredDistance = Math.pow((a - x), 2) + Math.pow((y - b), 2);
        double squaredRadiiSum = Math.pow((r + c), 2);
        return squaredDistance <= squaredRadiiSum;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getXVel() {
        return this.Xvel;
    }

    public double getYVel() {
        return this.Yvel;
    }

    public void setXVel(double Xvel) {
        this.Xvel = Xvel;
    }

    public void setYVel(double Yvel) {
        this.Yvel = Yvel;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getMass() {
        return this.mass;
    }
}