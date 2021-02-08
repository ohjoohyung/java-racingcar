package racingcar.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Cars {
    private static final String NO_NAME_ERROR = "반드시 자동차 이름을 입력하셔야 합니다.";
    private static final String COMMA = ",";
    private static final String COMMA_SPACE = ", ";

    private List<Car> cars;

    public Cars(List<Car> cars) {
        this.cars = cars;
    }

    public static Cars createCars(String carNames) {
        List<Car> carList = new ArrayList<>();
        String[] carNamesArray = carNamesSplit(carNames);
        for (String carName : carNamesArray) {
            carList.add(new Car(carName));
        }
        return new Cars(carList);
    }

    private static String[] carNamesSplit(String carNames) {
        String[] carNamesArray = carNames.split(COMMA);
        if (carNamesArray.length == 0) {
            throw new IllegalArgumentException(NO_NAME_ERROR);
        }
        return carNamesArray;
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public void move(MoveValueStrategy moveValueStrategy) {
        for (Car car : cars) {
            car.move(moveValueStrategy);
        }
    }

    public String getWinners() {
        HashMap<Integer, List<String>> carPositionHashMap = new HashMap<>();
        int maxPosition = 0;
        for (Car car : cars) {
            maxPosition = findWinnerPosition(car, carPositionHashMap, maxPosition);
        }
        List<String> winners = carPositionHashMap.get(maxPosition);
        return getWinnerNames(winners);
    }

    private int findWinnerPosition(Car car, HashMap<Integer, List<String>> carPositionHashMap,
        int maxPosition) {
        initCarPositionHashMap(car, carPositionHashMap);
        carPositionHashMap.get(car.getPosition()).add(car.getName());
        if (maxPosition < car.getPosition()) {
            maxPosition = car.getPosition();
        }
        return maxPosition;
    }

    private void initCarPositionHashMap(Car car, HashMap<Integer, List<String>> carPositionHashMap) {
        if (!carPositionHashMap.containsKey(car.getPosition())) {
            carPositionHashMap.put(car.getPosition(), new ArrayList<>());
        }
    }

    private String getWinnerNames(List<String> winners) {
        StringBuilder sb = new StringBuilder();
        sb.append(winners.get(0));
        for (int i = 1; i < winners.size(); i++) {
            sb.append(COMMA_SPACE);
            sb.append(winners.get(i));
        }
        return sb.toString();
    }
}