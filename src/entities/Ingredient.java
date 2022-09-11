package entities;

public class Ingredient {
    private String name;
    private double qnt;
    private double rate;

    public Ingredient(String name, double qnt, double rate) {
        this.name = name;
        this.qnt = qnt;
        this.rate = rate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQnt(double qnt) {
        this.qnt = qnt;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public double getQnt() {
        return qnt;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object object)
    {
        if(this.getClass()!=object.getClass())
        {
            return false;
        }
        else {
            Ingredient otherIngredient=(Ingredient) object;
            return this.getName().equals(otherIngredient.getName());
        }
    }
    @Override
    public String toString() {
        return "IngridentName"+this.getName()+" qnt"+this.getQnt()+" Rate"+this.getRate();
    }
}
