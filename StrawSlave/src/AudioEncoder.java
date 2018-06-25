import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.xuggler.IStreamCoder;

import java.io.File;

/**
 * Created by jakeu on 2018. 6. 10..
 */
public class AudioEncoder {
    // MARK: properties
    private File inFile;
    private File outFile;
    private int kbps;
    public AudioEncoder(File s, File d, int kbps){
        this.inFile = s;
        this.outFile = d;
        this.bitRateConvert(kbps);
    }

    public void bitRateConvert(int kbps){
        // create a media reader
        IMediaReader mediaReader = ToolFactory.makeReader(this.inFile.getPath());

        // create a media writer
        IMediaWriter mediaWriter = ToolFactory.makeWriter(this.outFile.getPath(), mediaReader);

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
