package com.musclemate.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val Figure = Color(0xFFE8EDF7)
private val Accent = Color(0xFF3D8BFF)
private val Joint = Color(0xFF7CB2FF)

@Composable
fun ExerciseAnimation(
    exerciseName: String,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "exercise")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "movement"
    )

    Box(modifier) {
        Canvas(Modifier.fillMaxSize()) {
            val name = exerciseName.lowercase()
            when {
                name.contains("bench press") || name.contains("push up") ->
                    drawPress(progress, name.contains("incline"))
                name.contains("fly") || name.contains("crossover") ->
                    drawFly(progress)
                name.contains("curl") ->
                    drawCurl(progress)
                name.contains("pushdown") || name.contains("skull") || name.contains("kickback") ->
                    drawTriceps(progress)
                name.contains("row") || name.contains("pulldown") || name.contains("pull up") ->
                    drawPull(progress, name.contains("pulldown") || name.contains("pull up"))
                name.contains("squat") || name.contains("leg press") ->
                    drawSquat(progress, name.contains("leg press"))
                name.contains("extension") ->
                    drawLegExtension(progress)
                name.contains("deadlift") ->
                    drawDeadlift(progress)
                name.contains("raise") || name.contains("face pull") || name.contains("press") ->
                    drawShoulder(progress)
                else ->
                    drawGeneral(progress)
            }
        }
    }
}

private fun DrawScope.drawPress(p: Float, incline: Boolean) {
    val cx = size.width * .5f
    val base = size.height * .66f
    val bodyY = base - 55f
    val angle = if (incline) -0.22f else 0f
    val elbow = 45f + 20f * p
    drawPerson(cx, bodyY, 48f, 105f, 0f)
    val shoulder = Offset(cx - 5f, bodyY - 5f)
    val hand = Offset(cx - 5f, bodyY - 80f + elbow)
    drawLine(Figure, shoulder, hand, 10f, StrokeCap.Round)
    drawLine(Figure, Offset(cx + 5f, bodyY - 5f), Offset(cx + 5f, bodyY - 80f + elbow), 10f, StrokeCap.Round)
    drawBar(Offset(cx - 78f, bodyY - 85f + elbow), Offset(cx + 78f, bodyY - 85f + elbow), angle)
}

private fun DrawScope.drawFly(p: Float) {
    val cx = size.width * .5f
    val y = size.height * .55f
    drawPerson(cx, y, 48f, 105f, 0f)
    val spread = 85f - 55f * p
    drawLine(Figure, Offset(cx - 8f, y), Offset(cx - spread, y + 45f), 10f, StrokeCap.Round)
    drawLine(Figure, Offset(cx + 8f, y), Offset(cx + spread, y + 45f), 10f, StrokeCap.Round)
    drawCircle(color = Accent, center = Offset(cx - spread, y + 45f), radius = 7f)
    drawCircle(color = Accent, center = Offset(cx + spread, y + 45f), radius = 7f)
}

private fun DrawScope.drawCurl(p: Float) {
    val cx = size.width * .5f
    val y = size.height * .54f
    val lift = 55f * p
    drawPerson(cx, y, 48f, 110f, 0f)
    drawLine(Figure, Offset(cx - 28f, y + 5f), Offset(cx - 45f, y + 58f - lift), 10f, StrokeCap.Round)
    drawLine(Figure, Offset(cx + 28f, y + 5f), Offset(cx + 45f, y + 58f - lift), 10f, StrokeCap.Round)
    drawCircle(color = Accent, center = Offset(cx - 45f, y + 58f - lift), radius = 8f)
    drawCircle(color = Accent, center = Offset(cx + 45f, y + 58f - lift), radius = 8f)
}

private fun DrawScope.drawTriceps(p: Float) {
    val cx = size.width * .5f
    val y = size.height * .57f
    val down = 55f * p
    drawPerson(cx, y, 48f, 110f, 0f)
    drawLine(Figure, Offset(cx - 28f, y), Offset(cx - 40f, y + 40f + down), 10f, StrokeCap.Round)
    drawLine(Figure, Offset(cx + 28f, y), Offset(cx + 40f, y + 40f + down), 10f, StrokeCap.Round)
    drawLine(Accent, Offset(cx - 40f, y + 40f + down), Offset(cx - 40f, size.height - 28f), 5f)
    drawLine(Accent, Offset(cx + 40f, y + 40f + down), Offset(cx + 40f, size.height - 28f), 5f)
}

private fun DrawScope.drawPull(p: Float, vertical: Boolean) {
    val cx = size.width * .5f
    val y = size.height * (.57f - .12f * p)
    drawPerson(cx, y, 48f, 105f, 0f)
    if (vertical) {
        drawLine(Accent, Offset(cx - 70f, 30f), Offset(cx + 70f, 30f), 7f, StrokeCap.Round)
        drawLine(Figure, Offset(cx - 25f, y), Offset(cx - 55f, 35f), 10f, StrokeCap.Round)
        drawLine(Figure, Offset(cx + 25f, y), Offset(cx + 55f, 35f), 10f, StrokeCap.Round)
    } else {
        drawLine(Accent, Offset(cx - 95f, y + 10f), Offset(cx + 95f, y + 10f), 7f, StrokeCap.Round)
        drawLine(Figure, Offset(cx - 25f, y), Offset(cx - 65f, y + 50f - 30f * p), 10f, StrokeCap.Round)
        drawLine(Figure, Offset(cx + 25f, y), Offset(cx + 65f, y + 50f - 30f * p), 10f, StrokeCap.Round)
    }
}

private fun DrawScope.drawSquat(p: Float, machine: Boolean) {
    val cx = size.width * .5f
    val knee = 35f * p
    val y = size.height * .48f + knee
    drawPerson(cx, y, 48f, 100f, 0f)
    val hip = Offset(cx, y + 40f)
    val kneeL = Offset(cx - 42f, y + 72f + knee)
    val kneeR = Offset(cx + 42f, y + 72f + knee)
    drawLine(Figure, hip, kneeL, 11f, StrokeCap.Round)
    drawLine(Figure, hip, kneeR, 11f, StrokeCap.Round)
    drawLine(Figure, kneeL, Offset(cx - 55f, size.height - 25f), 11f, StrokeCap.Round)
    drawLine(Figure, kneeR, Offset(cx + 55f, size.height - 25f), 11f, StrokeCap.Round)
    if (!machine) drawBar(Offset(cx - 70f, y - 35f), Offset(cx + 70f, y - 35f), 0f)
}

private fun DrawScope.drawLegExtension(p: Float) {
    val cx = size.width * .5f
    val y = size.height * .48f
    drawPerson(cx, y, 48f, 90f, 0f)
    val leg = 60f * p
    drawLine(Figure, Offset(cx - 15f, y + 45f), Offset(cx - 40f, y + 95f), 12f, StrokeCap.Round)
    drawLine(Figure, Offset(cx + 15f, y + 45f), Offset(cx + 40f, y + 95f - leg), 12f, StrokeCap.Round)
    drawLine(Accent, Offset(cx - 55f, size.height - 28f), Offset(cx + 55f, size.height - 28f), 6f, StrokeCap.Round)
}

private fun DrawScope.drawDeadlift(p: Float) {
    val cx = size.width * .5f
    val bend = 28f * p
    val hip = Offset(cx + bend, size.height * .52f)
    val shoulder = Offset(cx - bend, size.height * .38f)
    drawCircle(color = Figure, center = Offset(shoulder.x, shoulder.y - 25f), radius = 20f)
    drawLine(Figure, shoulder, hip, 13f, StrokeCap.Round)
    drawLine(Figure, hip, Offset(cx - 55f, size.height - 28f), 12f, StrokeCap.Round)
    drawLine(Figure, hip, Offset(cx + 55f, size.height - 28f), 12f, StrokeCap.Round)
    drawLine(Figure, shoulder, Offset(cx - 65f, size.height * .64f), 10f, StrokeCap.Round)
    drawLine(Figure, shoulder, Offset(cx + 5f, size.height * .64f), 10f, StrokeCap.Round)
    drawBar(Offset(cx - 70f, size.height * .68f), Offset(cx + 70f, size.height * .68f), 0f)
}

private fun DrawScope.drawShoulder(p: Float) {
    val cx = size.width * .5f
    val y = size.height * .55f
    val lift = 65f * p
    drawPerson(cx, y, 48f, 110f, 0f)
    drawLine(Figure, Offset(cx - 25f, y), Offset(cx - 65f, y - 15f - lift), 10f, StrokeCap.Round)
    drawLine(Figure, Offset(cx + 25f, y), Offset(cx + 65f, y - 15f - lift), 10f, StrokeCap.Round)
    drawCircle(color = Accent, center = Offset(cx - 65f, y - 15f - lift), radius = 8f)
    drawCircle(color = Accent, center = Offset(cx + 65f, y - 15f - lift), radius = 8f)
}

private fun DrawScope.drawGeneral(p: Float) {
    val cx = size.width * .5f
    drawPerson(cx, size.height * .55f - 25f * p, 48f, 110f, 0f)
    drawCircle(color = Accent, center = Offset(cx, size.height * .55f - 25f * p), radius = 70f, style = Stroke(3f))
}

private fun DrawScope.drawPerson(cx: Float, bodyY: Float, head: Float, body: Float, rotation: Float) {
    drawCircle(color = Figure, center = Offset(cx, bodyY - body * .55f), radius = head * .42f)
    drawLine(Figure, Offset(cx, bodyY - body * .3f), Offset(cx, bodyY + body * .45f), 13f, StrokeCap.Round)
    drawLine(Figure, Offset(cx, bodyY), Offset(cx - 45f, bodyY + 38f), 11f, StrokeCap.Round)
    drawLine(Figure, Offset(cx, bodyY), Offset(cx + 45f, bodyY + 38f), 11f, StrokeCap.Round)
    drawLine(Figure, Offset(cx, bodyY + body * .45f), Offset(cx - 42f, bodyY + body), 12f, StrokeCap.Round)
    drawLine(Figure, Offset(cx, bodyY + body * .45f), Offset(cx + 42f, bodyY + body), 12f, StrokeCap.Round)
    drawCircle(color = Joint, center = Offset(cx, bodyY), radius = 6f)
}

private fun DrawScope.drawBar(a: Offset, b: Offset, angle: Float) {
    val mid = Offset((a.x + b.x) / 2f, (a.y + b.y) / 2f)
    val half = (a.x - b.x).let { kotlin.math.abs(it) / 2f }
    val dx = cos(angle) * half
    val dy = sin(angle) * half
    drawLine(Accent, Offset(mid.x - dx, mid.y - dy), Offset(mid.x + dx, mid.y + dy), 7f, StrokeCap.Round)
    drawCircle(color = Accent, center = Offset(mid.x - dx, mid.y - dy), radius = 10f)
    drawCircle(color = Accent, center = Offset(mid.x + dx, mid.y + dy), radius = 10f)
}
