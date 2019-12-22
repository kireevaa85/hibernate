# hibernate
Hibernate

Основные операции (методы) Session:
• find, get – поиск и загрузка объекта по его id. Объект сразу получается в состоянии persistent
• load – то же, что и get, но возвращается пустой proxy. Загрузка из БД произойдет в момент первого
обращения к любому свойству объекта
• persist – меняет состояние объекта из transient в persistent. Выполняет insert. Бросает
PersistentObjectException если задан id
• merge – меняет состояние объекта из transient или detached в persistent. Для transient работает
аналогично persist. Для detached выполняет загрузку из БД и обновляет в контексте. По
завершении сессии или коммита транзакции выполняет update
• remove – удаление объекта из БД и контекста. Меняет состояние на transient.
IllegalArgumentException если объект в состоянии detached
• createQuery – создание объекта запроса
• getEntityGraph – получение, ранее определенного графа объектов
• save – то же, что и persist, но гарантированно вернет идентификатор. Плюс для detached объектов
каждый раз генерирует новый id
• update – меняет состояние объекта из detached в persistent. Если объект в состоянии transient
бросает исключение
• saveOrUpdate – вызывает save или update в зависимости от изначального состояния объекта
