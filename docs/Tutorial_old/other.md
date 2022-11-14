<br> Имея доступ к исходному коду `Tutorial` мы можем увидеть, что интересующий нас экран отображается в `SimpleActivity`, а сама верстка лежит в файле `activity_simple`. Поэтому, в данном случае мы можем воспользоваться `white-box тестированием` (детальнее про этот тип тестирования можно узнать [здесь](https://ru.wikipedia.org/wiki/%D0%A2%D0%B5%D1%81%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5_%D0%B1%D0%B5%D0%BB%D0%BE%D0%B3%D0%BE_%D1%8F%D1%89%D0%B8%D0%BA%D0%B0)). Альтернативный способ тестирования (black-box) будет рассмотрен в следующих уроках.


<br> Если автотест пишет тот же человек, кто и писал код самого приложения, то не должно возникнуть вопросов по указанным выше `id` в методах `withId`, `layoutId` и `viewClass`. В нашем же случае код Tutorial был написан до нас, поэтому нам важно понять, как их найти. При помощи [Layout inspector](https://medium.com/androiddevelopers/layout-inspector-1f8d446d048) можно проанализировать структуру элементов на экране, узнать id нужных элементов. В дальнейшем, глобальным поиском по проекту можно найти файлы `layoutId` и `viewClass`, с которыми связаны найденные `id`. Подход PageObject позволяет разделить работу по написанию автотестов между разработчиками приложения и разработчиками тестов: разработчики приложения могут создавать объекты `Screen` для нужных экранов со всеми идентификаторами элементов, а разработчики автотестов по готовым объектам моделей экранов реализовывать тесткейсы. 
<br> Для поиска нужного `View` можно использовать сразу несколько matcher-ов. Например, если у какого-то элемента нет `id`, мы можем найти его с помощью нескольких matcher-ов. 
<br> В объекте SimpleScreen переопределены `layoutId` и `ViewClass`. Если их корректно не проинициализировать (например, присвоить `null`), то на работоспособность теста это влиять не будет. Но мы рекомендуем не игнорировать их и корректно инициализировать. Это поможет при разработке и дальнейшей поддержки понимать, с каким `ViewClass` и `layoutId` связан конкретный Screen.



<br> Обращаемся к созданному нами выше PageObject-у `SimpleScreen`. У этого объекта мы объявили поле заголовка `title`. Внутри блока `title{ }` доступны различные методы. 
<br> Сейчас нас интересуют методы `isVisible()` и `hasText()`. 
<br> Элемент `title` объявлен с типом `KTextView`. Это класс-обертка в `Kaspresso`, которая реализует интерфейсы `TextViewActions` и `TextViewAssertions`. Первый определяет набор действий, которые могут быть выполнены над заголовком, а второй - набор проверок. 
<br> Рекомендуем посмотреть код этих интерфейсов, их родителей и аналогичные интерфейсы для других элементов.



<br> Рассмотрим сам тест. Благодаря реализации паттерна Page object и Kotlin DSL код теста становится простым и понятным: сперва мы проверили корректность отображения нужных элементов, затем ввели текст в поле ввода, нажали кнопку и проверили, что заголовок изменился. Однако, код любого теста - это реализация определенных тест-кейсов. Сам же тест-кейс - это некий сценарий (последовательность шагов), написанный на человеческом языке тестировщиком. Этот набор шагов может со временем меняться, поэтому спустя какое-то время возникнет потребность в редактировании теста. Помимо этого, тест может не всегда проходить успешно. Чтобы тест было легко поддерживать и он оставался понятным спустя долгое время, он должен быть разделен на шаги, идентичные указанным в тест-кейсах. Комментарии будут не самым лучшим решением, так как в логах не будет понятно, на каком шаге упал тест. Для этого можно воспользоваться специальными методами Kaspresso (например, `step()`).

```kotlin
class SimpleTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<SimpleActivity>()

    @Test
    fun test() =
        before {

        }.after {

        }.run {
            step("Open Simple Screen") {
                SimpleScreen {
                    title {
                        isVisible()
                        hasText(R.string.simple_activity_default_title)
                        hasTextColor(R.color.black)
                    }

                    button {
                        isVisible()
                        withText(R.string.simple_activity_change_title_button)
                        isClickable()
                    }
                    input {
                        isVisible()
                        hasHint(R.string.simple_activity_input_hint)
                        hasEmptyText()
                    }
                }
            }

            step("Type \" Kaspresso \"") {
                SimpleScreen {
                    input.typeText("Kaspresso")
                    closeSoftKeyboard()
                    button.click()
                }
            }

            step("Check title content") {
                SimpleScreen {
                    title.hasText("Kaspresso")
                }
            }
        }
}
```
<br> У каждого теста могут быть свои предусловия (определенные состояния девайса), а после его выполнения состояние девайса должно быть возвращено в исходное. Секции before и after нужны для настройки состояния до и после прогона теста. Например, это может быть включение Bluetooth. До выполнения теста необходимо включить его, а после - выключить. Более подробно эти секции описаны в следующих примерах. 
<br> Step представляет собой абстракцию, которая реагирует на все события шага (например: шаг стартует, шаг завершается успехом или неудачей). Внутри одной секции step может быть объявлены другие секции step. По умолчанию, абстракция step добавляет логирование и скриншотинг (возможность кастомизаций набора действий описаны в следующих примерах). Таким образом, после прогона теста можно будет посмотреть подробные записи логирования, которые будут полезны для дальнейшей поддержки тестов в рабочем состоянии и устранении проблем. Скришоты будут делаться по окончании шага (по одному на каждую step-секцию) и перед завершением теста в случае возникновения ошибки. Данное поведение основывается на философии Kaspresso о возможном изменении состояния после каждого шага. Если необходимо больше скриншотов, то их можно сделать с помощью вызова метода `device.screenshots.take("Additional_screenshot")`. Доступный функционал `device` описан в следующих примерах.


<br> Расширим код теста `test()` в `SimpleActivityTest` проверкой, что заголовок отображается и содержит ожидаемый текст.

```kotlin
class SimpleActivityTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() {
        SimpleActivityScreen {
            title {
                isVisible()
                hasText(R.string.simple_activity_default_title)
            }
        }
    }
}
```