package com.example.helloworld.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import com.example.helloworld.core.Person;

public class PersonDAO {
    private static final String dbPath = "./rocksdb-data/";
    private static final Options options = new Options().setCreateIfMissing(true);
    private static RocksDB rocksDB;

    static {
        RocksDB.loadLibrary();
        try {
            rocksDB = RocksDB.open(options, dbPath);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }

    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public Optional<Person> findById(Long id) {
        Optional<Person> person = Optional.empty();
        try {
            byte[] key = longToBytes(id);
            byte[] data = rocksDB.get(key);
            try (final InputStream byteArrayInputStream = new ByteArrayInputStream(data);
                    final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                person = Optional.ofNullable((Person) objectInputStream.readObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return person;
    }

    public Person create(Person person) {
        try (
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            byte[] key = longToBytes(person.getId());
            objectOutputStream.writeObject(person);
            objectOutputStream.flush();
            rocksDB.put(key, byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return person;
    }

    public List<Person> findAll() {
        List<Person> people = new ArrayList<>(3);
        try {
            List<byte[]> keys = Arrays.asList(longToBytes(1), longToBytes(2), longToBytes(3));
            List<byte[]> values = rocksDB.multiGetAsList(keys);
            for (byte[] value : values) {
                try (final InputStream byteArrayInputStream = new ByteArrayInputStream(value);
                        final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                    people.add((Person) objectInputStream.readObject());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }
}
