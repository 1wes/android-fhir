package com.google.android.fhir.datacapture.views

import android.R
import android.app.Instrumentation
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import com.google.android.fhir.datacapture.views.Findable.StringId.ContentDesc

const val DEFAULT_WAIT = 10_000L
const val LONG_WAIT = 20_000L
const val VERY_LONG_WAIT = 40_000L

abstract class BaseRobot(
  private val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation(),
  var context: Context = instrumentation.targetContext,
  val uiDevice: UiDevice = UiDevice.getInstance(instrumentation)
) {

  internal fun clickNegativeDialogButton() {
    clickOn(Findable.ViewId(R.id.button2))
  }

  internal fun clickPositiveDialogButton() {
    clickOn(Findable.ViewId(R.id.button1))
  }

  internal fun pressBack() {
    uiDevice.pressBack()
  }

  protected fun isVisible(findable: Findable, timeout: Long = DEFAULT_WAIT) =
      waitFor(findable, timeout) ?: throw RuntimeException(findable.errorMessage(this))

  protected fun UiObject2.swipeLeft() {
    customSwipe(Direction.LEFT)
  }

  protected fun UiObject2.swipeRight() {
    customSwipe(Direction.RIGHT)
  }

  protected fun clickOn(findable: Findable, timeout: Long = DEFAULT_WAIT) {
    isVisible(findable, timeout).click()
  }

  protected fun longClickOn(findable: Findable) {
    isVisible(findable).click(1000L)
  }

  protected fun clickOnTab(textId: Int) {
    clickOn(ContentDesc(textId), LONG_WAIT)
  }

  protected fun waitFor(milliseconds: Long) {
    waitFor(Findable.Text("WILL_NEVER_EXIST"), milliseconds)
  }

  private fun waitFor(
    findable: Findable,
    timeout: Long = DEFAULT_WAIT
  ): UiObject2? =
      uiDevice.wait(Until.findObject(findable.selector(this)), timeout)

  private fun UiObject2.customSwipe(
    direction: Direction,
    percent: Float = 0.8f
  ) {
    swipe(direction, percent)
  }
}
