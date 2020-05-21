package metro;

import java.util.Objects;

public class Line {
    private String number;
    private String name;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return number.equals(line.number) &&
                name.equals(line.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name);
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}

