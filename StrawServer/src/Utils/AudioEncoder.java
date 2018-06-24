package Utils;

import java.io.File;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.xuggler.IStreamCoder;

/**
 * Created by jakeu on 2018. 6. 10..
 */
public class AudioEncoder {
    // MARK: properties
    private File inFile;
    private File outFile;

    public AudioEncoder(String filePath, String fileName){
        this.inFile = new File(filePath);
        this.outFile = new File("Database/2000Audios/"+fileName+".mp3");
    }

    public void bitRateConvert(int kbps){
        // create a media reader
        IMediaReader mediaReader = ToolFactory.makeReader(inFile.getPath());

        // create a media writer
        IMediaWriter mediaWriter = ToolFactory.makeWriter(outFile.getPath(), mediaReader);

        // add a writer to the reader, to create the output file
        mediaReader.addListener(mediaWriter);

        // add a IMediaListner to the writer to change bit rate
        mediaWriter.addListener(new MediaListenerAdapter() {
            @Override
            public void onAddStream(IAddStreamEvent event) {
                IStreamCoder streamCoder = event.getSource().getContainer().getStream(event.getStreamIndex()).getStreamCoder();
                streamCoder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, false);
                streamCoder.setBitRate(kbps*1000);
                streamCoder.setBitRateTolerance(0);

            }
        });

        // read and decode packets from the source file and
        // and dispatch decoded audio and video to the writer
        while (mediaReader.readPacket() == null);

        System.out.println("Encoding done with "+kbps+" kbps");
    }
}
