package online.paescape.util;


import net.runelite.client.RuneLite;
import online.paescape.Client;

import javax.sound.midi.*;
import javax.sound.sampled.*;
import java.applet.Applet;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public final class Signlink implements Runnable {

    public static final RandomAccessFile[] cache_idx = new RandomAccessFile[11];
    public static Sequencer music = null;

    public static Sequence sequence = null;
    public static Synthesizer synthesizer;
    public static RandomAccessFile cache_dat = null;
    public static Applet mainapp = null;
    public static String midi = null;
    public static int midiVolume;
    public static int fadeMidi;
    public static int wavevol;
    private static boolean active;
    private static int threadliveid;
    private static InetAddress socketip;
    private static int socketreq;
    private static Socket socket = null;
    private static int threadreqpri = 1;
    private static Runnable threadreq = null;
    private static String urlreq = null;
    private static DataInputStream urlstream = null;
    private static int savelen;
    private static String savereq = null;
    private static byte[] savebuf = null;
    private static boolean midiplay;
    private static int midipos;
    private static boolean waveplay;
    private static int wavepos;
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    private Position curPosition;

    private Signlink() {
    }

    public static void startpriv(InetAddress inetaddress) {
        threadliveid = (int) (Math.random() * 99999999D);
        if (active) {
            try {
                Thread.sleep(500L);
            } catch (Exception _ex) {
            }
            active = false;
        }
        socketreq = 0;
        threadreq = null;
        savereq = null;
        urlreq = null;
        socketip = inetaddress;
        Thread thread = new Thread(new Signlink());
        thread.setDaemon(true);
        thread.start();
        while (!active)
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }
    }

    /**
     * Sets the volume for the midi synthesizer.
     *
     * @param value
     */
    public static void setVolume(int value) throws MidiUnavailableException {
        int CHANGE_VOLUME = 7;
        midiVolume = value;
        synthesizer = MidiSystem.getSynthesizer();
        if (synthesizer.getDefaultSoundbank() == null) {
            try {
                ShortMessage volumeMessage = new ShortMessage();
                for (int i = 0; i < 16; i++) {
                    volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, CHANGE_VOLUME, midiVolume);
                    volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 39, midiVolume);
                    MidiSystem.getReceiver().send(volumeMessage, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MidiChannel[] channels = synthesizer.getChannels();
            for (int c = 0; channels != null && c < channels.length; c++) {
                channels[c].controlChange(CHANGE_VOLUME, midiVolume);
                channels[c].controlChange(39, midiVolume);
            }
        }
    }

    // TODO
    public static String findcachedir() {
        String cacheLoc = RuneLite.CACHE_DIR.getAbsolutePath() + File.separator;
        File cacheDir = new File(cacheLoc);
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        return cacheLoc;
    }

    public static synchronized Socket opensocket(int i) throws IOException {
        for (socketreq = i; socketreq != 0; )
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }

        if (socket == null)
            throw new IOException("could not open socket");
        else
            return socket;
    }

    public static synchronized DataInputStream openurl(String s) throws IOException {
        for (urlreq = s; urlreq != null; )
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }

        if (urlstream == null)
            throw new IOException("could not open: " + s);
        else
            return urlstream;
    }

    public static synchronized void startthread(Runnable runnable, int i) {
        threadreqpri = i;
        threadreq = runnable;
    }

    public static final synchronized boolean wavesave(byte[] abyte0, int i) {
        if (i > 0x1e8480) {
            return false;
        }
        if (savereq != null) {
            return false;
        } else {
            wavepos = (wavepos + 1) % 5;
            savelen = i;
            savebuf = abyte0;
            waveplay = true;
            savereq = "sound" + wavepos + ".wav";
            return true;
        }
    }

    public static final synchronized boolean wavereplay() {
        if (savereq != null) {
            return false;
        } else {
            savebuf = null;
            waveplay = true;
            savereq = "sound" + wavepos + ".wav";
            return true;
        }
    }

    public static final synchronized void midisave(byte[] abyte0, int i) {
        if (i > 0x1e8480) {
            return;
        }
        if (savereq != null) {
            return;
        } else {
            midipos = (midipos + 1) % 5;
            savelen = i;
            savebuf = abyte0;
            midiplay = true;
            savereq = "jingle" + midipos + ".mid";
            return;
        }
    }

    public static String getCacheDirectory() {

        return findcachedir();
    }

    public void run() {
        active = true;
        String s = findcachedir();
        try {
            cache_dat = new RandomAccessFile(s + "main_file_cache.dat", "rw");
            for (int j = 0; j < 10; j++) {
                cache_idx[j] = new RandomAccessFile(s + "main_file_cache.idx" + j, "rw");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        for (int i = threadliveid; threadliveid == i; ) {
            if (socketreq != 0) {
                try {
                    socket = new Socket(socketip, socketreq);
                } catch (Exception _ex) {
                    socket = null;
                }
                socketreq = 0;
            } else if (threadreq != null) {
                Thread thread = new Thread(threadreq);
                thread.setDaemon(true);
                thread.start();
                thread.setPriority(threadreqpri);
                threadreq = null;
            } else if (savereq != null) {
                if (savebuf != null)
                    try {
                        FileOutputStream fileoutputstream = new FileOutputStream(s + savereq);
                        fileoutputstream.write(savebuf, 0, savelen);
                        fileoutputstream.close();
                    } catch (Exception _ex) {
                    }
                if (waveplay) {
                    String wave = s + savereq;
                    waveplay = false;
                    AudioInputStream audioInputStream = null;
                    try {
                        audioInputStream = AudioSystem.getAudioInputStream(new File(wave));
                    } catch (UnsupportedAudioFileException e1) {
                        e1.printStackTrace();
                        return;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        return;
                    }
                    AudioFormat format = audioInputStream.getFormat();
                    SourceDataLine auline = null;
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    try {
                        auline = (SourceDataLine) AudioSystem.getLine(info);
                        auline.open(format);
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    if (auline.isControlSupported(FloatControl.Type.PAN)) {
                        FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
                        if (curPosition == Position.RIGHT)
                            pan.setValue(1.0f);
                        else if (curPosition == Position.LEFT)
                            pan.setValue(-1.0f);
                    }
                    auline.start();
                    int nBytesRead = 0;
                    byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
                    try {
                        while (nBytesRead != -1) {
                            nBytesRead = audioInputStream.read(abData, 0, abData.length);
                            if (nBytesRead >= 0)
                                auline.write(abData, 0, nBytesRead);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    } finally {
                        auline.drain();
                        auline.close();
                    }
                }
                if (midiplay) {
                    midi = s + savereq;
                    try {
                        if (music != null) {
                            music.stop();
                            music.close();
                        }
                        playMidi(midi);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    midiplay = false;
                }
                savereq = null;
            } else if (urlreq != null) {
                try {
                    System.out.println("urlstream");
                    urlstream = new DataInputStream((new URL(mainapp.getCodeBase(), urlreq)).openStream());
                } catch (Exception _ex) {
                    urlstream = null;
                }
                urlreq = null;
            }
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }
        }

    }

    /**
     * Plays the specified midi sequence.
     *
     * @param location
     */
    private void playMidi(String location) {
		/*music = null;
		synthesizer = null;
		sequence = null;
		File midiFile = new File(location);
		try {
			sequence = MidiSystem.getSequence(midiFile);
			music = MidiSystem.getSequencer();
			music.open();
			music.setSequence(sequence);
		} catch (Exception e) {
			System.err.println("Problem loading MIDI file.");
			e.printStackTrace();
			return;
		}
		if (music instanceof Synthesizer) {
			synthesizer = (Synthesizer) music;
		} else {
			try {
				synthesizer = MidiSystem.getSynthesizer();
				synthesizer.open();
				if (synthesizer.getDefaultSoundbank() == null) {
					music.getTransmitter().setReceiver(MidiSystem.getReceiver());
				} else {
					music.getTransmitter().setReceiver(synthesizer.getReceiver());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		music.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		music.start();*/
    }

    enum Position {
        LEFT, RIGHT, NORMAL
    }

}

