package net.cad.repositories

import net.cad.models.BlogPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BlogPostRepository : JpaRepository<BlogPost, UUID>
