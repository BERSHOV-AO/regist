package ru.nak.ied.regist.utils

object TO_Data_AGV_1100_2T {

    val toMap = mutableMapOf<String, String>()

    init {
        toMap["Корпус - очистка от загрязнений"] = "30"
        toMap["Корпус - контроль резьбовых соединений, протяжка"] = "30"
        toMap["Позиционирующие ролики - проверить на наличие люфтов"] = "30"

        toMap["Сканер безопасности - очистка сканера безопасности"] = "30"
        toMap["Сканер безопасности - проверка крепления кабеля сканера"] = "30"

        toMap["Бампер - контроль резьбовых соединений, протяжка"] = "30"
        toMap["Лицевая панель - контроль целостности защиты панели"] = "30"

        toMap["Электрика - проверка работы звука"] = "30"
        toMap["Электрика - Контроль целостности кнопок и панели"] = "30"

        toMap["PIN - Очистка (c разборкой)"] = "90"
        toMap["PIN - Контроль резбовых соединений, протяжка"] = "30"
        toMap["PIN - Осмотр, контроль люфтов"] = "30"
        toMap["PIN - Проверить укладку проводов"] = "30"
        toMap["PIN - Проверить работу"] = "30"
        toMap["PIN - Контроль верхнего положения -> max 3мм. не доходит"] = "30"
        toMap["PIN - Смазка"] = "30"

        toMap["Подъемник - Проверить устан. штифты на кулачке"] = "30"
        toMap["Подъемник - Контроль резбовых соединений, протяжка"] = "30"
        toMap["Подъемник - Проверить крепление опорных подшипников"] = "30"
        toMap["Подъемник - Проверить работу подъемника"] = "30"
        toMap["Подъемник - Проверка срабат. концевиков (настройка)"] = "30"

        toMap["Drive unit - Проверить крепление поъемной втулки"] = "30"
        toMap["Drive unit - Контроль резбовых соединений, протяжка"] = "30"
        toMap["Drive unit - Проверка укладки и целостности кабеля"] = "30"
        toMap["Drive unit - Осмотр контроль люфтов"] = "30"
        toMap["Drive unit - Контроль натяжения цепей"] = "90"
        toMap["Drive unit - Смазка цепей"] = "30"

        toMap["Датчик трека и RFID - Проверить крепление"] = "30"
        toMap["Датчик трека и RFID - Проверить целостность"] = "30"
        toMap["Датчик трека и RFID - Проверить целостность и укладку кабелей"] = "30"
        toMap["Датчик трека и RFID - Проверить крепление и наличие защиты"] = "30"
        toMap["Датчик трека и RFID - Высота от пола -> не более 20 мм."] = "30"

        toMap["Колеса приводные - Контроль диаметра наружного -> min Ø 145"] = "30"
        toMap["Колеса приводные - Проверить продольный люфт"] = "30"
        toMap["Колеса приводные - Проверить крепление крышки колеса -> до упора, с LOCTITE 243"] = "30"

        toMap["Приводные звездочки - Очистка от мусора"] = "30"
        toMap["Приводные звездочки - Проверить устан. штифты на звездах"] = "30"
        toMap["Приводные звездочки - Проверить крепление крышки звездочки -> до упора, с LOCTITE 243"] = "30"

        toMap["Колеса поворотные - Очистка от загрязнений"] = "30"
        toMap["Колеса поворотные - Проверить продольный люфт"] = "30"

        toMap["Колеса задние - Проверить продольный люфт"] = "30"
    }

    fun addToMap(key: String, nameTO: String) {
        toMap[key] = nameTO
    }
}