package com.survivingcodingbootcamp.blog.storage.repository;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.storage.HashtagStorage;
import org.springframework.stereotype.Service;

@Service
public class HashtagStorageJpalmpl implements HashtagStorage {
    private HashtagRepository hashRepo;

    public HashtagStorageJpalmpl(HashtagRepository hashRepo) {
        this.hashRepo = hashRepo;
    }

    @Override
    public Iterable<Hashtag> retrieveAllHashtags() {
        return hashRepo.findAll();
    }



    @Override
    public void save(Hashtag hashtagToAdd) {
        hashRepo.save(hashtagToAdd);
    }

    @Override
    public Hashtag createOrRetrieveHashtag(String name) {

        if(hashRepo.findByName(name) == null){
            Hashtag hashtag = new Hashtag(name);
            hashRepo.save(hashtag);
            return hashtag;
        }
        else
        {
            return hashRepo.findByName(name);
        }
    }

    @Override
    public Hashtag findById(Long id) {
        return hashRepo.findById(id).get();
    }

    @Override
    public Hashtag findByName(String name) {
        return hashRepo.findByName(name);
    }
}

