package Utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by jakeu on 2018. 6. 10..
 */
public class AudioEncoder {
    // MARK: properties
    private File inFile;
    private File outFile;

    public AudioEncoder(File inFile, String fileName){
        this.inFile = inFile;
        this.outFile = new File("Database/2000Audios/"+fileName+".mp3");
    }

    public void changeSampleRate()throws InterruptedException, UnsupportedAudioFileException, IOException {
        AudioInputStream ais;
        AudioInputStream eightKhzInputStream = null;
        ais = AudioSystem.getAudioInputStream(inFile);
        AudioFormat sourceFormat = ais.getFormat();

        if (true) {
            AudioFileFormat sourceFileFormat = AudioSystem.getAudioFileFormat(inFile);
            AudioFileFormat.Type targetFileType = sourceFileFormat.getType();

            AudioFormat targetFormat = new AudioFormat(
                    sourceFormat.getEncoding(),
                    2000f,
                    sourceFormat.getSampleSizeInBits(),
                    sourceFormat.getChannels(),
                    sourceFormat.getFrameSize(),
                    2000f,
                    sourceFormat.isBigEndian());
            if (!AudioSystem.isFileTypeSupported(targetFileType) || ! AudioSystem.isConversionSupported(targetFormat, sourceFormat)) {
                throw new IllegalStateException("Conversion not supported!");
            }
            eightKhzInputStream = AudioSystem.getAudioInputStream(targetFormat, ais);
            int nWrittenBytes = 0;

            nWrittenBytes = AudioSystem.write(eightKhzInputStream, targetFileType, outFile);
            System.out.println("nWrittenBytes: " + nWrittenBytes);
        }
    }
}
