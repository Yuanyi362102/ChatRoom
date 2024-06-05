package Communication;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class RecevieTips {
    public  void fun() throws InterruptedException {
        String audioFilePath = "images/tip.wav"; // 替换为你的音频文件路径

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);

            // 添加 LineListener 来监听音频播放事件
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        System.out.println("发出提示音");
                    }
                }
            });

            // 开始播放音频
            clip.start();

            // 等待用户输入，以保持程序运行，直到音频播放结束
            System.out.println("收到消息,正在播放音频");
            System.in.read();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}