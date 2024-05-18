package shop.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.demo.domain.Refresh;
import shop.demo.repository.RefreshRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshService {

    private final RefreshRepository refreshRepository;

    @Transactional
    public Refresh saveRefresh(Refresh refresh) {
        return refreshRepository.save(refresh);
    }
    public boolean isRefreshExists(String refresh) {
        return refreshRepository.existsByRefresh(refresh);
    }

    @Transactional
    public void deleteRefresh(String refresh) {
        refreshRepository.deleteByRefresh(refresh);
    }
}
