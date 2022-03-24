package com.example.app07

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.PI
import kotlin.math.atan2

//커스텀 뷰 만들기
// VolumeControlView.kt 파일 생성. 이후 MainActivity가 AppCompatActivity를 상속받는 것처럼
// VolumeControlView 클래스도 AppCompatImageView를 상속받아야 한다.
// 이때 생성자를 만들어주어야 하는데 context, attrs 있는 걸로 해준 뒤
// activity_main.xml의 ImageView 태그를 VolumeControlView로 변경해준다.

//터치로 볼륨 조정 가능케 하는 뷰를 별도로 만들기. 소리를 변경하기 위한 각도 정보 제공
class VolumeControlView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {
    var mx = 0.0f
    var my = 0.0f
    var tx = 0.0f
    var ty = 0.0f
    var angle = 180.0f

    //누군가 나를 구현했다: 내 리스너 정보를 세팅. 현재는 null
    var listener: VolumeListener? = null

    //어디서든 volumecontrol을 할 수 있도록 인터페이스 제작. (독립적인 위젯)
    public interface VolumeListener {
        public fun onChanged(angle: Float): Unit
    }

    //리스너 세팅
    public fun setVolumeListener(listener: VolumeListener) {
        this.listener = listener
    }

    //터치하면 터치한 위치를 토대로 각도 계산. 그 각도를 가지고 이미지 회전 작업 수행.
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            tx = event.getX(0)
            ty = event.getY(0)
            angle = getAngle(tx, ty)
            invalidate() // 화면 무효화 하고 다시 그림 -> onDraw 자동 호출됨. (invalidate -> onDraw 호출)'

            //리스너가 나를 호출한 곳에 있는 onChanged 메소드를 호출할 수 있게 함-> 계산한 각도를 넘겨준다.
            //그럼 그 각도를 소리 값을 조정하는데 사용하면 된다.
            if (listener != null) {
                listener?.onChanged(angle)
            }
            return true
        } else {
            return false
        }
    }

    //화면 그리는 메소드: 화면의 중심을 기준으로 해서 각도만큼 회전.
    override fun onDraw(canvas: Canvas?) {
        canvas?.rotate(angle, width / 2.0f, height / 2.0f)
        super.onDraw(canvas)
    }

    //각도 계산하는 메소드: 12시가 0이고 6시가 180도. 왼쪽은 음수 값, 오른쪽은 양수 값 리턴.
    fun getAngle(x1: Float, y1: Float): Float {
        mx = x1 - (width / 2.0f)
        my = (height / 2.0f) - y1
        return (atan2(mx, my) * 180.0f / PI).toFloat()
    }
}