package ru.nak.ied.regist.utils

object TOData {

    val toMap = mutableMapOf<String, String>()

    fun addToMap(key: String, nameTO: String) {
        toMap[key] = nameTO
    }
}


/**
 *  <string name="body__cleaning">Корпус - очистка от загрязнений</string>
 *     <string name="body__Inspection_connections_broaching">Корпус - контроль резьбовых соединений, протяжка</string>
 *
 *     <string name="positioning_rollers">Позиционирующие ролики - проверить на наличие люфтов</string>
 *     <string name="battery_connectors">Разъем АКБ - проверка целостности, протяжка</string>
 *     <string name="locks">Замки - наличие и срабатывание</string>
 *
 *     <string name="security_scanner__cleaning">Сканер безопасности - очистка сканера безопасности</string>
 *     <string name="security_scanner__fastenings">Сканер безопасности - проверка крепления кабеля сканера</string>
 *
 *     <string name="bumper">Бампер - контроль резьбовых соединений, протяжка</string>
 *     <string name="front_panel">Лицевая панель - контроль целостности защиты панели</string>
 *
 *     <string name="electric__sound">Электрика - проверка работы звука</string>
 *     <string name="electric__button_panel">Электрика - Контроль целостности кнопок и панели</string>
 *
 *     <string name="pin__1">PIN - Очистка (c разборкой)</string>
 *     <string name="pin__2">PIN - Контроль резбовых соединений, протяжка</string>
 *     <string name="pin__3">PIN - Осмотр, контроль люфтов</string>
 *     <string name="pin__4">PIN - Проверить укладку проводов</string>
 *     <string name="pin__5">PIN - Проверить работу</string>
 *     <string name="pin__6">PIN - Контроль верхнего положения</string>
 *     <string name="pin__7">PIN - Смазка</string>
 *
 *     <string name="lift__1">Подъемник - Проверить устан. штифты на кулачке</string>
 *     <string name="lift__2">Подъемник - Контроль резбовых соединений, протяжка</string>
 *     <string name="lift__3">Подъемник - Проверить крепление опорных подшипников</string>
 *     <string name="lift__4">Подъемник - Проверить работу подъемника</string>
 *     <string name="lift__5">Подъемник - Проверка срабат. концевиков (настройка)</string>
 *
 *     <string name="drive_unit__1">Drive unit - Проверить крепление поъемной втулки</string>
 *     <string name="drive_unit__2">Drive unit - Контроль резбовых соединений, протяжка</string>
 *     <string name="drive_unit__3">Drive unit - Проверка укладки и целостности кабеля</string>
 *     <string name="drive_unit__4">Drive unit - Осмотр контроль люфтов</string>
 *     <string name="drive_unit__5">Drive unit - Контроль натяжения цепей</string>
 *     <string name="drive_unit__6">Drive unit - Смазка цепей</string>
 *
 *     <string name="track_sensor_and_RFID__1">Датчик трека и RFID - Проверить крепление</string>
 *     <string name="track_sensor_and_RFID__2">Датчик трека и RFID - Проверить целостность</string>
 *     <string name="track_sensor_and_RFID__3">Датчик трека и RFID - Проверить целостность и укладку кабелей</string>
 *     <string name="track_sensor_and_RFID__4">Датчик трека и RFID - Проверить крепление и наличие защиты</string>
 *     <string name="track_sensor_and_RFID__5">Датчик трека и RFID - Высота от пола</string>
 *
 *     <string name="drive_wheels__1">Колеса приводные - Контроль диаметра наружного</string>
 *     <string name="drive_wheels__2">Колеса приводные - Проверить продольный люфт</string>
 *     <string name="drive_wheels__3">Колеса приводные - Проверить крепление крышки колеса</string>
 *
 *     <string name="drive_stars__1">Приводные звездочки - Очистка от мусора</string>
 *     <string name="drive_stars__2">Приводные звездочки - Проверить устан. штифты на звездах</string>
 *     <string name="drive_stars__3">Приводные звездочки - Проверить крепление крышки звездочки</string>
 *
 *     <string name="turning_wheels__1">Колеса поворотные - Очистка от загрязнений</string>
 *     <string name="turning_wheels__2">Колеса поворотные - Проверить продольный люфт</string>
 *
 *     <string name="back_wheels__1">Колеса задние - Проверить продольный люфт</string>
 */