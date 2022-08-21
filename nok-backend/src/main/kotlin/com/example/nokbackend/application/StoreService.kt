package com.example.nokbackend.application

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.store.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository,
    private val storeQueryRepository: StoreQueryRepository,
    private val memberRepository: MemberRepository,
    private val menuRepository: MenuRepository,
    private val storeImageRepository: StoreImageRepository,
    private val authenticationService: AuthenticationService,
    private val imageService: ImageService
) {

    fun registerStore(registerStoreRequest: RegisterStoreRequest, storeImages: List<MultipartFile>, menuImages: List<MultipartFile?>): Long {
        check(registerStoreRequest.owner.role == Member.Role.STORE) { "상점 주인으로만 등록할 수 있습니다" }
        check(registerStoreRequest.menus.size == menuImages.size) { "메뉴 이미지 갯수가 정확하지 않습니다" }

//        authenticationService.confirmAuthentication(
//            ConfirmAuthenticationRequest(
//                registerStoreRequest.owner.authenticationId,
//                registerStoreRequest.owner.email,
//                registerStoreRequest.owner.authenticationCode
//            ),
//            Authentication.Type.REGISTER
//        )

        val owner = saveOwner(registerStoreRequest.owner)

        val store = saveStore(registerStoreRequest, owner)

        saveStoreImages(storeImages, store)

        saveMenus(registerStoreRequest.menus, menuImages, store)

        return store.id
    }

    private fun saveMenus(registerMenuRequests: List<RegisterMenuRequest>, menuImages: List<MultipartFile?>, store: Store) {
        registerMenuRequests.forEachIndexed { index, registerMenuRequest ->
            val imageUrls = menuImages.uploadImage()

            val menu = registerMenuRequest.toEntity(store, imageUrls[index])
            menuRepository.save(menu)
        }
    }

    private fun saveOwner(registerStoreRequest: RegisterMemberRequest): Member {
        val owner = registerStoreRequest.toEntity()
        return memberRepository.save(owner)
    }

    private fun saveStore(registerStoreRequest: RegisterStoreRequest, owner: Member): Store {
        val store = registerStoreRequest.toEntity(owner.id)
        return storeRepository.save(store)
    }

    private fun saveStoreImages(storeImages: List<MultipartFile>, store: Store) {
        storeImages.uploadImage()
            .forEach {
                val storeImage = StoreImage(store, it, StoreImage.Status.ACTIVE)
                storeImageRepository.save(storeImage)
            }
    }

    private fun List<MultipartFile?>.uploadImage(): List<String> = map {
        it?.let {
            val uuid = UUID.randomUUID().toString()
            imageService.uploadFile(it, uuid)
        } ?: ""
    }

    fun findByCondition(findStoreCondition: FindStoreCondition): List<StoreResponse> {
        return storeQueryRepository.findByCondition(findStoreCondition)
            .map { StoreResponse(it) }
    }

    fun findStoreInfo(storeId: Long): StoreDetailResponse {
        val store = storeRepository.findByIdCheck(storeId)

        check(store.status == Store.Status.ACTIVE) { "삭제되거나 등록대기 상태인 점포입니다" }

        val storeImages = storeImageRepository.findByStoreAndStatus(store, StoreImage.Status.ACTIVE)

        val menus = menuRepository.findByStoreAndStatus(store, Menu.Status.ACTIVE)

        return StoreDetailResponse(store, storeImages, menus)
    }
}