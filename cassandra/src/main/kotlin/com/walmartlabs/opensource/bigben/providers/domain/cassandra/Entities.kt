package com.walmartlabs.opensource.bigben.providers.domain.cassandra

import com.datastax.driver.mapping.annotations.*
import com.walmartlabs.opensource.bigben.entities.*
import com.walmartlabs.opensource.bigben.extns.bucket
import java.time.ZonedDateTime

/**
 * Created by smalik3 on 2/26/18
 */
@Table(keyspace = "bigben", name = "buckets")
data class BucketC(@PartitionKey override var id: ZonedDateTime? = null,
                   override var status: EventStatus? = null,
                   override var count: Long? = null,
                   @Column(name = "processed_at") override var processedAt: ZonedDateTime? = null,
                   @Column(name = "modified_at") override var updatedAt: ZonedDateTime? = null) : Bucket

@Table(keyspace = "bigben", name = "events")
data class EventC(@ClusteringColumn @Column(name = "event_time") override var eventTime: ZonedDateTime? = null,
                  @ClusteringColumn(1) override var id: String? = null,
                  @PartitionKey @Column(name = "bucket_id") var bucketId: ZonedDateTime? = eventTime?.bucket(),
                  @PartitionKey(1) override var shard: Int? = null,
                  override var status: EventStatus? = null,
                  override var error: String? = null,
                  override var tenant: String? = null,
                  @Column(name = "xref_id") override var xrefId: String? = null,
                  @Column(name = "processed_at") override var processedAt: ZonedDateTime? = null,
                  override var payload: String? = null,
                  @Transient override var eventResponse: EventResponse? = null) : Event

@Table(keyspace = "bigben", name = "lookups")
data class EventLookupC(@PartitionKey override var tenant: String? = null,
                        @PartitionKey(1) @Column(name = "xref_id") override var xrefId: String? = null,
                        @Column(name = "bucket_id") override var bucketId: ZonedDateTime? = null,
                        override var shard: Int? = null,
                        @Column(name = "event_time") override var eventTime: ZonedDateTime? = null,
                        @Column(name = "event_id") override var eventId: String? = null,
                        override var payload: String? = null) : EventLookup