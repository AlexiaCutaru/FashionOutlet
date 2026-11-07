
package com.example.outletfashion.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.outletfashion.ui.theme.*

@Composable
fun BottomNavBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val labels = listOf(
        "HOME", "SHOP", "SAVED", "CLUB", "YOU"
    )

    Column(
        modifier = Modifier.background(LuxuryWhite)
    ) {
        HorizontalDivider(color = LuxuryBorder)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            labels.forEachIndexed { index, label ->
                val selected = selectedTab == index

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onTabSelected(index) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MinimalNavIcon(
                        index = index,
                        color = if (selected)
                            LuxuryBlack else LuxuryGray
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = label,
                        fontSize = 9.sp,
                        letterSpacing = 0.5.sp,
                        color = if (selected)
                            LuxuryBlack else LuxuryGray
                    )

                    Spacer(Modifier.height(5.dp))

                    Box(
                        modifier = Modifier
                            .width(16.dp)
                            .height(2.dp)
                            .background(
                                if (selected)
                                    LuxuryBlack else LuxuryWhite
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun MinimalNavIcon(
    index: Int,
    color: Color
) {
    Canvas(
        modifier = Modifier.size(23.dp)
    ) {
        val sx = size.width / 24f
        val sy = size.height / 24f

        fun point(x: Float, y: Float) =
            Offset(x * sx, y * sy)

        fun line(
            x1: Float, y1: Float,
            x2: Float, y2: Float
        ) {
            drawLine(
                color = color,
                start = point(x1, y1),
                end = point(x2, y2),
                strokeWidth = 1.7.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        when (index) {
            0 -> {
                // Home
                val roof = Path().apply {
                    moveTo(3f * sx, 11f * sy)
                    lineTo(12f * sx, 4f * sy)
                    lineTo(21f * sx, 11f * sy)
                }

                drawPath(
                    roof,
                    color,
                    style = Stroke(
                        width = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )

                line(5f, 10f, 5f, 21f)
                line(19f, 10f, 19f, 21f)
                line(5f, 21f, 19f, 21f)
                line(10f, 21f, 10f, 15f)
                line(14f, 15f, 14f, 21f)
                line(10f, 15f, 14f, 15f)
            }

            1 -> {
                // Shopping bag
                val bag = Path().apply {
                    moveTo(5f * sx, 8f * sy)
                    lineTo(19f * sx, 8f * sy)
                    lineTo(20f * sx, 21f * sy)
                    lineTo(4f * sx, 21f * sy)
                    close()
                }

                drawPath(
                    bag,
                    color,
                    style = Stroke(1.7.dp.toPx())
                )

                drawArc(
                    color = color,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = point(9f, 3f),
                    size = androidx.compose.ui.geometry.Size(
                        6f * sx,
                        10f * sy
                    ),
                    style = Stroke(1.7.dp.toPx())
                )
            }

            2 -> {
                // Heart
                val heart = Path().apply {
                    moveTo(12f * sx, 21f * sy)
                    cubicTo(
                        8f * sx, 17f * sy,
                        2f * sx, 13f * sy,
                        3f * sx, 8f * sy
                    )
                    cubicTo(
                        4f * sx, 2f * sy,
                        10f * sx, 3f * sy,
                        12f * sx, 7f * sy
                    )
                    cubicTo(
                        15f * sx, 2f * sy,
                        21f * sx, 3f * sy,
                        21f * sx, 9f * sy
                    )
                    cubicTo(
                        21f * sx, 14f * sy,
                        16f * sx, 18f * sy,
                        12f * sx, 21f * sy
                    )
                    close()
                }

                drawPath(
                    heart,
                    color,
                    style = Stroke(1.7.dp.toPx())
                )
            }

            3 -> {
                // Club - minimalist star
                val star = Path().apply {
                    moveTo(12f * sx, 2f * sy)
                    lineTo(14.5f * sx, 9.5f * sy)
                    lineTo(22f * sx, 12f * sy)
                    lineTo(14.5f * sx, 14.5f * sy)
                    lineTo(12f * sx, 22f * sy)
                    lineTo(9.5f * sx, 14.5f * sy)
                    lineTo(2f * sx, 12f * sy)
                    lineTo(9.5f * sx, 9.5f * sy)
                    close()
                }

                drawPath(
                    star,
                    color,
                    style = Stroke(1.7.dp.toPx())
                )
            }

            4 -> {
                // Profile
                drawCircle(
                    color = color,
                    radius = 4f * sx,
                    center = point(12f, 7f),
                    style = Stroke(1.7.dp.toPx())
                )

                drawArc(
                    color = color,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = point(4f, 12f),
                    size = androidx.compose.ui.geometry.Size(
                        16f * sx,
                        11f * sy
                    ),
                    style = Stroke(1.7.dp.toPx())
                )
            }
        }
    }
}
