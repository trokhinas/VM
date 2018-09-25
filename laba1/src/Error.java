enum Error {
    IER0(0),
    IER1(1),
    IER2(2),
    IER3(3),
    IER4(4);

    private byte code;

    Error(int i) {
        code = (byte) i;
    }
    public int getCode() {
        return code;
    }
    @Override
    public String toString() {
        switch(this){
            case IER0:return "Нет ошибки, требуемая точность достигнута";
            case IER1:return "Требуемая точность не достигнута (N мало)";
            case IER2:return "Nребуемая точность не достигается. Модуль разности между " +
                    "двумя последовательными интерполяционными значениями перестаёт " +
                    "уменьшаться. ";
            case IER3:return "В векторе X нарушен порядок возрастания аргументов";
            case IER4:return "Значение аргумента XX не принадлежит отрезку.";
        }
        return "FATAL ERROR!";
    }
}